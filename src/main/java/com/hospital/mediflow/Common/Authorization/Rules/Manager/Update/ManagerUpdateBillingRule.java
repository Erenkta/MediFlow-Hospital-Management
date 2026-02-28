package com.hospital.mediflow.Common.Authorization.Rules.Manager.Update;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
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
        BillingRequestDto data = (BillingRequestDto) context.getPayload();
        if(data.departmentId() != null){
            if (!Objects.equals(data.departmentId(), context.getUser().getResourceId())) {
                throw new AccessDeniedException(generateRelationExceptionMessage(data.departmentId(),action().name(),role().name(),ResourceType.DEPARTMENT.name()));
            }
            if(data.departmentId() != null && !docDepDataService.isDepartmentAppointmentRelationsExists(context.getUser().getResourceId(),data.appointmentId())){
                throw new AccessDeniedException(generateRelationExceptionMessage(data.appointmentId(),action().name(),role().name(),ResourceType.APPOINTMENT.name()));
            }
        }

        if(!billingDataService.isBillingDepartmentRelationExists(context.getResourceId(),context.getUser().getResourceId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(context.getResourceId(),action().name(),role().name(),resource().name()));
        }
    }
}
