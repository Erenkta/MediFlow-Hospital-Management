package com.hospital.mediflow.Appointment.Domain.Dtos;

public interface AvailableAppointments {
    Long getDepartmentId();
    String getDepartmentName();
    Boolean getAppointmentAvailable();
}
