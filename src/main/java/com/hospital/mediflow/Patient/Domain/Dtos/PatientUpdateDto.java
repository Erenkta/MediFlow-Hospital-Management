package com.hospital.mediflow.Patient.Domain.Dtos;

import com.hospital.mediflow.Common.Annotations.ValidatePhone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

/*
* This body will be used for updating the patient with MANAGER role
* */
public record PatientUpdateDto(
        @ValidatePhone(message = "Phone format is not valid.")
        @NotBlank(message = "Phone must not be empty.")
        String phone,
        @Email()
        @NotBlank
        String email
) {
}
