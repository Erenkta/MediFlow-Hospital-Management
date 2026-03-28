package com.hospital.mediflow.Billing.Domain.Dtos;

import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Billing.Enums.BillingType;

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
        List<BillingType> type,
        LocalDateTime billingDateStart,
        LocalDateTime billingDateEnd,
        LocalDateTime paymentDateStart,
        LocalDateTime paymentDateEnd
) {
    public BillingFilterDto ManagerFilter(Long departmentId){
        return new BillingFilterDto(this.patientId,this.appointmentId,departmentId,this.amountLessThan,this.amountGreaterThan,this.status,type,this.billingDateStart,this.billingDateEnd,paymentDateStart,paymentDateEnd);
    }
    public BillingFilterDto PatientFilter(Long patientId){
        return new BillingFilterDto(patientId,appointmentId,departmentId,this.amountLessThan,this.amountGreaterThan,this.status,type,this.billingDateStart,this.billingDateEnd,paymentDateStart,paymentDateEnd);
    }
}
