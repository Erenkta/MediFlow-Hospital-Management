package com.hospital.mediflow.Billing.Domain.Dtos;

import com.hospital.mediflow.Billing.Enums.BillingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BillingFilterDto(
        String patientName,
        BigDecimal amountLessThan,
        BigDecimal amountGreaterThan,
        List<BillingStatus> status,
        LocalDateTime billingDateStart,
        LocalDateTime billingDateEnd
) {
}
