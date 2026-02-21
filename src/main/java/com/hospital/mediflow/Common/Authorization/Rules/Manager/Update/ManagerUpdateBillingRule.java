package com.hospital.mediflow.Common.Authorization.Rules.Manager.Update;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManagerUpdateBillingRule implements ActionRule {
    private final BillingDataService billingDataService;
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
        return AccessType.UPDATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        BillingAccessData data = (BillingAccessData) context.getPayload();
        if (!Objects.equals(data.departmentId(), context.getUser().getResourceId())) {
            throw new AccessDeniedException(generateRelationExceptionMessage(data.departmentId(),role().name(),ResourceType.DEPARTMENT.name()));
        }
        if(!docDepDataService.isDepartmentAppointmentRelationsExists(context.getUser().getResourceId(),data.appointmentId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(data.appointmentId(),role().name(),ResourceType.APPOINTMENT.name()));
        }
        if(!billingDataService.isBillingDepartmentRelationExists(data.billingId(),context.getUser().getResourceId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(data.billingId(),role().name(),resource().name()));
        }
    }
}
