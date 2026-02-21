package com.hospital.mediflow.Common.Authorization.Policy.Patient;


import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Policy.AuthorizationPolicyDeprecated;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientPolicy extends AuthorizationPolicyDeprecated {
    private final MedicalRecordDataService medicalRecordDataService;
    private final AppointmentDataService appointmentDataService;
    private final BillingDataService billingDataService;

    public void assertCanAccessMedicalRecord(User subject, Long resourceId, AccessType action) {
        if(!action.equals(AccessType.READ_BY_ID)){
            throw new AccessDeniedException(generateAccessExceptionMessage(action,subject.getRole().name()));
        }

        boolean isAccessible = medicalRecordDataService.isPatientRecordRelationExists(subject.getResourceId(),resourceId);
        if(!isAccessible){
            throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Medical Record"));
        }
    }

    public void assertCanAccessAppointment(User subject, Long resourceId, AccessType action) {
        switch(action){
            case DELETE ,PATCH->{
                if(!appointmentDataService.isAppointmentPatientRelationExists(resourceId,subject.getResourceId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Appointment"));
                }
            }
            default -> throw new IllegalStateException(String.format("There is no policy for the action type : %s",action.name()));
        }
    }

    public void assertCanAccessBilling(User subject, BillingAccessData data, AccessType action) {
        switch(action){
            case READ_BY_ID ->{
                if(!billingDataService.isBillingPatientRelationExists(data.billingId(),subject.getResourceId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(data.billingId(),subject.getRole().name(),"Billing"));
                }
            }
            default -> throw new IllegalStateException(String.format("There is no policy for the action type : %s",action.name()));
        }
    }
}
