package com.hospital.mediflow.Common.Authorization.Rules.Manager.Create;

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
public class ManagerCreateAppointmentRule implements ActionRule {

    @Override
    public Role role() {
        return Role.MANAGER;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.APPOINTMENT;
    }

    @Override
    public AccessType action() {
        return AccessType.CREATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        Long departmentId = ((AppointmentRequestDto)context.getPayload()).departmentId();
        if(!Objects.equals(departmentId,context.getUser().getResourceId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(departmentId,action().name(),role().name(),resource().name()));
        }
    }
}
