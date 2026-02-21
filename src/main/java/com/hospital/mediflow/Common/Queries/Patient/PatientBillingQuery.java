package com.hospital.mediflow.Common.Queries.Patient;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Annotations.Access.Patient.PatientBillingAccess;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PatientBillingQuery {
    private final BillingService service;

    @PatientBillingAccess(type = AccessType.READ_BY_ID)
    public BillingResponseDto findBillingById(Long billingId) {
        return service.findBillingById(billingId);
    }
}
