package com.hospital.mediflow.Common.Authorization.Rules.Patient.Filter;

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
public class PatientBillingFilterRule implements FilterManageRule {

    @Override
    public Role role() {
        return Role.PATIENT;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.BILLING;
    }

    @Override
    public Object manageFilter(FilterManagerContext context) {
        BillingFilterDto existingFilter = (BillingFilterDto) context.getFilter();
        return existingFilter.PatientFilter(context.getUser().getResourceId());
    }
}