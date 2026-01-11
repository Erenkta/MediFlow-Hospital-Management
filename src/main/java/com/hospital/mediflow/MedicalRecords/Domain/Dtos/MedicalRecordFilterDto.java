package com.hospital.mediflow.MedicalRecords.Domain.Dtos;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record MedicalRecordFilterDto(
        Long doctorId,
        Long patientId,
        String doctorName,
        String patientName,
        String departmentName,
        LocalDateTime recordDateStart,
        LocalDateTime recordDateEnd
) {
}
