package com.hospital.mediflow.Patient.Domain.Dtos;

import com.hospital.mediflow.Common.Annotations.ValidateBirthDate;
import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import com.hospital.mediflow.Common.Annotations.ValidatePhone;
import com.hospital.mediflow.Patient.Enums.BloodGroupEnum;
import com.hospital.mediflow.Patient.Enums.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientRequestDto(
        @Size(min=2, max=50, message = "Firstname size must be between 2 and 50.", groups = OnUpdate.class)
        @NotBlank(message = "Firstname must not be empty.", groups = OnUpdate.class)
        String firstName,

        @Size(min=2, max=50, message = "Lastname size must be between 2 and 50.", groups = OnUpdate.class)
        @NotBlank(message = "Lastname must not be empty.", groups = OnUpdate.class)
        String lastName,

        @ValidateBirthDate(groups = OnUpdate.class)
        LocalDate birthDate,

        @ValidatePhone(message = "Phone format is not valid.", groups = {OnUpdate.class, OnManagerUpdate.class})
        @NotBlank(message = "Phone must not be empty.", groups = {OnUpdate.class, OnManagerUpdate.class})
        String phone,

        @Email(groups = {OnUpdate.class, OnManagerUpdate.class})
        @NotBlank(groups = {OnUpdate.class, OnManagerUpdate.class})
        String email,

        @ValidateEnum(enumClass = BloodGroupEnum.class, message = "Invalid blood group.", groups = OnManagerUpdate.class)
        BloodGroupEnum bloodGroup,

        @ValidateEnum(enumClass = GenderEnum.class, message = "Invalid gender value.", groups = OnManagerUpdate.class)
        GenderEnum gender
) {
    public PatientRequestDto(String phone, String email) {
        this(null, null, null, phone, email, null, null);
    }
    public interface OnUpdate {}
    public interface OnManagerUpdate {}
}
