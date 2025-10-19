package com.hospital.mediflow.Patient.Domain.Dtos;

import java.time.LocalDate;

public record PatientResponseDto(
     Long id,
     String firstName,
     String lastName,
     LocalDate birthDate,
     String phone,
     String email,
     String bloodGroup,
     String gender
) {
}
