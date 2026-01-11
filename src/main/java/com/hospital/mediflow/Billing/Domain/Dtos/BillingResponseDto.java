package com.hospital.mediflow.Billing.Domain.Dtos;

import com.hospital.mediflow.Billing.Enums.BillingStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillingResponseDto(
        Long id,
        Long patientId,
        BigDecimal amount,
        BillingStatus status,
        LocalDateTime billingDate
) {
}
