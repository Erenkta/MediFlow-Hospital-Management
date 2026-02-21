package com.hospital.mediflow.Common.Authorization.Policy.Manager;


import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Common.Annotations.AccessType;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Model.DoctorAccessData;
import com.hospital.mediflow.Common.Authorization.Policy.AuthorizationPolicy;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerPolicy extends AuthorizationPolicy {
    private final PatientDataService patientDataService;
    private final DoctorDepartmentDataService docDepDataService;
    private final SpecialtyDataService specialtyDataService;
    private final BillingDataService billingDataService;
    private final AppointmentDataService appointmentDataService;

    public void assertCanAccessPatient(User subject, Long resourceId, AccessType action) {
        boolean hasAccess =
                patientDataService.isDepartmentPatientRelationExists(
                        subject.getResourceId(), resourceId
                );

        if (!hasAccess) {
            throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Patient"));
        }
    }

    public void assertCanAccessMedicalRecord(User subject, Long resourceId, AccessType action) {
        boolean hasAccess =
                docDepDataService.isDepartmentDoctorRelationsExists(
                        resourceId, subject.getResourceId()
                );
        if (!hasAccess) {
            throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Medical Record"));
        }
    }

    public void assertCanAccessDoctor(User subject, DoctorAccessData data, AccessType action) {
        switch (action) {
            case CREATE ->{
               boolean hasAccess =  specialtyDataService.isSpecialtyDepartmentRelationExists(data.specialty(),subject.getResourceId());
                if (!hasAccess) {
                    String message = String.format("Access is Denied. Current Manager user does not have access to Doctor with specialty : %s and doctor code : %s",data.specialty(),data.doctorCode());
                    throw new AccessDeniedException(message);
                }            }
            case UPDATE,DELETE ->{
                boolean hasAccess =  docDepDataService.isDepartmentDoctorRelationsExists(data.doctorId(),subject.getResourceId());
                if (!hasAccess) {
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.doctorId(),subject.getRole().name(),"Doctor"));
                }
            }
            default -> throw new IllegalStateException(String.format("There is no policy for the action type : %s",action.name()));
        }
    }

    public void assertCanAccessAppointment(User subject, Long resourceId, AccessType action) {
        switch (action){
            case DELETE ,PATCH->{
                if(!appointmentDataService.isAppointmentManagerRelationExists(resourceId,subject.getResourceId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Doctor"));
                }
            }
            default -> throw new IllegalStateException(String.format("There is no policy for the action type : %s",action.name()));
        }
    }

    public void assertCanAccessBilling(User subject, BillingAccessData data, AccessType action) {
        switch (action) {
            case CREATE -> {
                if (!Objects.equals(subject.getResourceId(), data.departmentId())) {
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.departmentId(),subject.getRole().name(),"Department"));
                }
                if(!docDepDataService.isDepartmentAppointmentRelationsExists(subject.getResourceId(),data.appointmentId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.appointmentId(),subject.getRole().name(),"Appointment"));
                }
            }
            case READ_BY_FILTER -> {
                if(data.appointmentId() != null && !docDepDataService.isDepartmentAppointmentRelationsExists(subject.getResourceId(),data.appointmentId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.appointmentId(),subject.getRole().name(),"Appointment"));
                }
                if(!patientDataService.isDepartmentPatientRelationExists(subject.getResourceId(),data.patientId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.patientId(),subject.getRole().name(),"Patient"));
                }
            }
            case READ_BY_ID,DELETE->{
                if(!billingDataService.isBillingDepartmentRelationExists(data.billingId(),subject.getResourceId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.billingId(),subject.getRole().name(),"Billing"));
                }
            }
            case UPDATE -> {
                if (!Objects.equals(data.departmentId(), subject.getResourceId())) {
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.departmentId(),subject.getRole().name(),"Department"));
                }
                if(!docDepDataService.isDepartmentAppointmentRelationsExists(subject.getResourceId(),data.appointmentId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.appointmentId(),subject.getRole().name(),"Appointment"));
                }
                if(!billingDataService.isBillingDepartmentRelationExists(data.billingId(),subject.getResourceId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.billingId(),subject.getRole().name(),"Billing"));
                }
            }
            default -> throw new IllegalStateException(String.format("There is no policy for the action type : %s",action.name()));
        }
    }
}
