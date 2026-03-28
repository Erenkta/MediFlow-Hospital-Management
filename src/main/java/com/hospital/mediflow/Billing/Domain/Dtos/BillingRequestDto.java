package com.hospital.mediflow.Billing.Domain.Dtos;

import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Billing.Enums.BillingType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillingRequestDto(
        Long patientId,
        Long departmentId,
        Long appointmentId,
        BigDecimal amount,
        BillingStatus status,
        BillingType type,
        LocalDateTime paymentDate,
        LocalDateTime billingDate
) {
    @Override
    public LocalDateTime billingDate(){
        return billingDate == null ? LocalDateTime.now() : billingDate;
    }
}
