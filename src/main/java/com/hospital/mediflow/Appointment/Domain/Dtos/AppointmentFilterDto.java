package com.hospital.mediflow.Appointment.Domain.Dtos;

import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record AppointmentFilterDto(
        Long patientId,
        Long doctorId,
        String reason,
        LocalDateTime appointmentDateBefore,
        LocalDateTime appointmentDateAfter,
        List<AppointmentStatusEnum> status
){
}
