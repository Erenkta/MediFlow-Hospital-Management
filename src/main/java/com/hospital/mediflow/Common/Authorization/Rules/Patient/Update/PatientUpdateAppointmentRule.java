package com.hospital.mediflow.Common.Authorization.Rules.Patient.Update;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class PatientUpdateAppointmentRule implements ActionRule {
    private final AppointmentDataService appointmentDataService;
    @Override
    public Role role() {
        return Role.PATIENT;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.APPOINTMENT;
    }

    @Override
    public AccessType action() {
        return AccessType.UPDATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        Long patientId = appointmentDataService.findById(context.getResourceId()).patientId();
        if(!Objects.equals(patientId,context.getUser().getResourceId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(patientId,action().name(),role().name(),resource().name()));
        }
    }
}
