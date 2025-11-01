package com.hospital.mediflow.Appointment.Domain.Dtos;

import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record AppointmentRequestDto (
        @NotNull
        Long patientId,

        @NotNull
        Long doctorId,

        LocalDateTime appointmentDate,

        String reason
){
}
