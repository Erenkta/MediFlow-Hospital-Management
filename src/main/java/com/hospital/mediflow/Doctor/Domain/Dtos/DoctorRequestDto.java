package com.hospital.mediflow.Doctor.Domain.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import com.hospital.mediflow.Common.Annotations.ValidatePhone;
import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record DoctorRequestDto(
        @NotNull(message = "Title cannot be null")
        @ValidateEnum(enumClass = TitleEnum.class,message = "Invalid title value")
        TitleEnum title,

        @NotBlank(message = "First name cannot be empty")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @JsonIgnore
        Long doctorCode,

        @NotBlank(message = "Last name cannot be empty")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @NotNull(message = "Specialty cannot be null")
        @ValidateEnum(enumClass = SpecialtyEnum.class,message = "Invalid specialty value")
        SpecialtyEnum specialty,

        @NotBlank(message = "Phone cannot be empty")
        @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
        @ValidatePhone(message = "Invalid phone format")
        String phone,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email must be valid")
        @Size(max = 100)
        String email
) {}