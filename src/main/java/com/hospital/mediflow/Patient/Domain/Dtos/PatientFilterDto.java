package com.hospital.mediflow.Patient.Domain.Dtos;

import java.time.LocalDate;
import java.util.List;

public record PatientFilterDto(
        String firstName,
        String lastName,
        LocalDate birthBefore,
        LocalDate birthAfter,
        List<String> bloodGroup,
        String gender
) {
    public PatientFilterDto {
        if (bloodGroup != null && bloodGroup.isEmpty()) {
            bloodGroup = null;
        }
    }
}
