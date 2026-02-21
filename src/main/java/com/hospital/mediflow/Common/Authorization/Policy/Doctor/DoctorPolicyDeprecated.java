package com.hospital.mediflow.Common.Authorization.Policy.Doctor;


import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Authorization.Policy.AuthorizationPolicyDeprecated;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorPolicyDeprecated extends AuthorizationPolicyDeprecated {
    private final PatientDataService patientDataService;
    private final MedicalRecordDataService medicalRecordDataService;
    private final AppointmentDataService appointmentDataService;

    public void assertCanAccessPatient(User subject, Long resourceId, AccessType action) {
        if(action != AccessType.READ_BY_ID) {
            throw new AccessDeniedException(generateAccessExceptionMessage(action,subject.getRole().name()));
        }

        boolean hasRelation =  patientDataService
                .isDoctorPatientRelationExists(
                        subject.getResourceId(),
                        resourceId
                );

        if(!hasRelation){
            throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Patient"));
        }
    }
    public void assertCanAccessMedicalRecord(User subject, Long resourceId, AccessType action) {
        boolean isAccessible = medicalRecordDataService.isDoctorRecordRelationExists(resourceId,subject.getResourceId());
        if(!isAccessible){
            throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Medical Record"));
        }
    }
    public void assertCanAccessAppointment(User subject, Long resourceId, AccessType action) {
        switch(action){
            case DELETE ,PATCH->{
                if(!appointmentDataService.isAppointmentDoctorRelationExists(resourceId,subject.getResourceId())){
                    throw new AccessDeniedException(generateRelationExceptionMessage(resourceId,subject.getRole().name(),"Billing"));
                }
            }
            default -> throw new IllegalStateException(String.format("There is no policy for the action type : %s",action.name()));
        }
    }
}
