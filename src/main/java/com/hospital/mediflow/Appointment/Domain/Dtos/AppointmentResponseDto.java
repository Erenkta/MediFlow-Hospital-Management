package com.hospital.mediflow.Appointment.Domain.Dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record AppointmentResponseDto(
        Long id,
        Long patientId,
        Long doctorId,
        LocalDateTime appointmentDate,
        String reason
) {
}
