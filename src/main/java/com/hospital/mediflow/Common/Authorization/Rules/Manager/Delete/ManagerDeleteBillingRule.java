package com.hospital.mediflow.Common.Authorization.Rules.Manager.Delete;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManagerDeleteBillingRule implements ActionRule {
    private final BillingDataService billingDataService;

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
        return AccessType.DELETE;
    }

    @Override
    public void check(AuthorizationContext context) {
        BillingAccessData data = (BillingAccessData) context.getPayload();
        if(!billingDataService.isBillingDepartmentRelationExists(data.billingId(),context.getUser().getResourceId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(data.billingId(),action().name(),role().name(),resource().name()));
        }
    }
}
