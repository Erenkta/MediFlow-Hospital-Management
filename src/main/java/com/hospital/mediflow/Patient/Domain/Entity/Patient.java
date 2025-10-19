package com.hospital.mediflow.Patient.Domain.Entity;

import com.hospital.mediflow.Common.Annotations.ValidateBirthDate;
import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import com.hospital.mediflow.Common.Annotations.ValidatePhone;
import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Patient.Enums.BloodGroupEnum;
import com.hospital.mediflow.Patient.Enums.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Table(name = "patients",schema = "mediflow_schema")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Patient extends BaseEntity {

    @Id
    @SequenceGenerator(name = "patient_seq",sequenceName = "mediflow_schema.id_generator_seq",allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "patient_seq")
    private Long id;

    @Size(min=2,max=50,message = "Firstname size must be between 2 and 50.")
    @NotBlank(message = "Firstname must not be empty.")
    private String firstName;

    @Size(min=2,max=50,message = "Lastname size must be between 2 and 50.")
    @NotBlank(message = "Lastname must not be empty.")
    private String lastName;

    @Column(name = "date_of_birth")
    @ValidateBirthDate
    private LocalDate birthDate;

    @ValidatePhone(message = "Phone format is not valid.")
    @NotBlank(message = "Phone must not be empty.")
    private String phone;

    @Email(message = "{jakarta.validation.constraints.Email.message}")
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    @ValidateEnum(enumClass = BloodGroupEnum.class,message = "Invalid blood group.")
    private BloodGroupEnum bloodGroup;

    @Enumerated(EnumType.STRING)
    @ValidateEnum(enumClass = GenderEnum.class,message = "Invalid gender value.")
    private GenderEnum gender;

}
