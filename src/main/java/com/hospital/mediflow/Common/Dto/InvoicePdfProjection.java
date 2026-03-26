package com.hospital.mediflow.Common.Dto;

import java.time.LocalDateTime;

public interface InvoicePdfProjection {
    Long getId();
    String getCustomerName();
    String getCustomerLastName();
    LocalDateTime getBillingDate();
    LocalDateTime getAppointmentDate();
    String getAppointmentDescription();
    Double getAmount();
}