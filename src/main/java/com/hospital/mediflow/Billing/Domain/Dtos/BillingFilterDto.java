package com.hospital.mediflow.Billing.Domain.Dtos;

import com.hospital.mediflow.Billing.Enums.BillingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BillingFilterDto(
        Long patientId,
        Long appointmentId,
        Long departmentId,
        BigDecimal amountLessThan,
        BigDecimal amountGreaterThan,
        List<BillingStatus> status,
        LocalDateTime billingDateStart,
        LocalDateTime billingDateEnd
) {
    public BillingFilterDto ManagerFilter(Long departmentId,Long appointmentId){
        return new BillingFilterDto(this.patientId,appointmentId,departmentId,this.amountLessThan,this.amountGreaterThan,this.status,this.billingDateStart,this.billingDateEnd);
    }
    public BillingFilterDto PatientFilter(Long patientId){
        return new BillingFilterDto(patientId,appointmentId,departmentId,this.amountLessThan,this.amountGreaterThan,this.status,this.billingDateStart,this.billingDateEnd);
    }
}
