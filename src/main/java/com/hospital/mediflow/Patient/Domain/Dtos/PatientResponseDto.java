package com.hospital.mediflow.Patient.Domain.Dtos;

import java.util.Date;

public record PatientResponseDto(
     Long id,
     String firstName,
     String lastName,
     Date birthDate,
     String phone,
     String email,
     String bloodGroup,
     String gender
) {
}
