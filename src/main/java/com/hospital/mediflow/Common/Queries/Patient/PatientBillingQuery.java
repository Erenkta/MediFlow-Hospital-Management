package com.hospital.mediflow.Common.Queries.Patient;

import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PatientBillingQuery {
    private final BillingService service;

}
