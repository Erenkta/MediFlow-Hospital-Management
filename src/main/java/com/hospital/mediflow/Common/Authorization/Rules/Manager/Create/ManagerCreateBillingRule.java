package com.hospital.mediflow.Common.Authorization.Rules.Manager.Create;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Model.DoctorAccessData;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManagerCreateBillingRule implements ActionRule {
    private final DoctorDepartmentDataService docDepDataService;

    @Override
    public Role role() {
        return Role.MANAGER;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.BILLING;
    }

    @Override
    public AccessType action() {
        return AccessType.CREATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        BillingAccessData data = (BillingAccessData) context.getPayload();
        if (!Objects.equals(context.getUser().getResourceId(), data.departmentId())) {
            throw new AccessDeniedException(generateRelationExceptionMessage(data.departmentId(),role().name(),ResourceType.DEPARTMENT.name()));
        }
        if(!docDepDataService.isDepartmentAppointmentRelationsExists(context.getUser().getResourceId(),data.appointmentId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(data.appointmentId(),role().name(),ResourceType.APPOINTMENT.name()));
        }
    }
}
