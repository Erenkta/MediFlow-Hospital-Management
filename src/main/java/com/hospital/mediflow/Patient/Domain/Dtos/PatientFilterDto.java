package com.hospital.mediflow.Patient.Domain.Dtos;

import java.util.Date;

public record PatientFilterDto(
        String firstName,
        String lastName,
        Date birthDate,
        String bloodGroup,
        String gender
) {
}
