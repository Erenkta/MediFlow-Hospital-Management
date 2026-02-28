package com.hospital.mediflow.Common.Authorization.Rules.Manager.Filter;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.FilterManagerContext;
import com.hospital.mediflow.Common.Authorization.Rules.FilterManageRule;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerBillingFilterRule implements FilterManageRule {

    @Override
    public Role role() {
        return Role.MANAGER;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.BILLING;
    }

    @Override
    public Object manageFilter(FilterManagerContext context) {
        BillingFilterDto existingFilter = (BillingFilterDto) context.getFilter();
        return existingFilter.ManagerFilter(context.getUser().getResourceId());
    }
}