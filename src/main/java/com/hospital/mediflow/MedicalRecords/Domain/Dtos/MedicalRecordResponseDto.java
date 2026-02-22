package com.hospital.mediflow.MedicalRecords.Domain.Dtos;

import java.time.LocalDateTime;

public record MedicalRecordResponseDto(
        Long id,
        String doctorName,
        String patientName,
        String diagnosis,
        String treatment,
        String prescription,
        LocalDateTime recordDate
) {
}
