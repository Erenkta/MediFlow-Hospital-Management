package com.hospital.mediflow.Common.Authorization.Model;

public record BillingAccessData(
        Long billingId,
        Long departmentId,
        Long appointmentId,
        Long patientId
) {
}
