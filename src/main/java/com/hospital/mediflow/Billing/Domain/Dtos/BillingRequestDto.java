package com.hospital.mediflow.Billing.Domain.Dtos;

import com.hospital.mediflow.Billing.Enums.BillingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillingRequestDto(
        Long patientId,
        BigDecimal amount,
        BillingStatus status,
        LocalDateTime billingDate
) {
    @Override
    public LocalDateTime billingDate(){
        return billingDate == null ? LocalDateTime.now() : billingDate;
    }
}
