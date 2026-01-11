package com.hospital.mediflow.MedicalRecords.Domain.Dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MedicalRecordRequestDto(
        @NotNull
        Long doctorId,
        @NotNull
        Long patientId,
        @NotEmpty
        String diagnosis,
        @NotEmpty
        String treatment,
        @NotEmpty
        String prescription,
        LocalDateTime recordDate
) {
    @Override
    public LocalDateTime recordDate() {
        return this.recordDate == null ? LocalDateTime.now() : this.recordDate;
    }
}
