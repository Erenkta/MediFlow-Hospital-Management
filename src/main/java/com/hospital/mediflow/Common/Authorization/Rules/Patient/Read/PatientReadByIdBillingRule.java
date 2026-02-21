package com.hospital.mediflow.Common.Authorization.Rules.Patient.Read;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PatientReadByIdBillingRule implements ActionRule {
    private final BillingDataService billingDataService;

    @Override
    public Role role() {
        return Role.PATIENT;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.BILLING;
    }

    @Override
    public AccessType action() {
        return AccessType.READ_BY_ID;
    }

    @Override
    public void check(AuthorizationContext context) {
        BillingAccessData data = (BillingAccessData) context.getPayload();
        if(!billingDataService.isBillingPatientRelationExists(data.billingId(),context.getUser().getResourceId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(data.billingId(),role().name(),resource().name()));
        }
    }
}
