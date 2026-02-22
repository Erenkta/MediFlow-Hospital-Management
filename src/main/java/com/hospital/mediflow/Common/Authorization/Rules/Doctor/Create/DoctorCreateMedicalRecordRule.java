package com.hospital.mediflow.Common.Authorization.Rules.Doctor.Create;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DoctorCreateMedicalRecordRule implements ActionRule {
    private final AppointmentDataService appointmentDataService;

    @Override
    public Role role() {
        return Role.DOCTOR;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.MEDICAL_RECORD;
    }

    @Override
    public AccessType action() {
        return AccessType.CREATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        Long patientId = ((MedicalRecordRequestDto)context.getPayload()).patientId();
        boolean isAccessible = appointmentDataService.isAppointmentExists(context.getUser().getResourceId(),patientId);
        if(!isAccessible){
            throw new AccessDeniedException(generateRelationExceptionMessage(context.getResourceId(),action().name(),role().name(),resource().name()));
        }
    }
}
