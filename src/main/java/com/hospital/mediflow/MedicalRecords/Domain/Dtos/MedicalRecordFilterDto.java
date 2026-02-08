package com.hospital.mediflow.MedicalRecords.Domain.Dtos;

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
