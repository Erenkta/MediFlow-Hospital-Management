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

import java.util.Date;

public record PatientRequestDto(
    @Size(min=2, max=50, message = "Firstname size must be between 2 and 50.")
    @NotBlank(message = "Firstname must not be empty.")
     String firstName,

    @Size(min=2,max=50,message = "Lastname size must be between 2 and 50.")
    @NotBlank(message = "Lastname must not be empty.")
     String lastName,

    @Column(name = "date_of_birth")
    @ValidateBirthDate
     Date birthDate,

    @ValidatePhone(message = "Phone format is not valid.")
    @NotBlank(message = "Phone must not be empty.")
     String phone,

    @Email(message = "{jakarta.validation.constraints.Email.message}")
    @NotBlank
     String email,

    @Enumerated(EnumType.STRING)
    @ValidateEnum(enumClass = BloodGroupEnum.class,message = "Invalid blood group.")
     BloodGroupEnum bloodGroup,

    @Enumerated(EnumType.STRING)
    @ValidateEnum(enumClass = GenderEnum.class,message = "Invalid gender value.")
     GenderEnum gender
) {
}
