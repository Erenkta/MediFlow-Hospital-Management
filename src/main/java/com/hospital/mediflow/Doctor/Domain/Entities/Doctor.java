package com.hospital.mediflow.Doctor.Domain.Entities;

import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import com.hospital.mediflow.Common.Annotations.ValidatePhone;
import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "doctors",schema = "mediflow_schema")
@Entity
@Getter
@Setter
@ToString(exclude = {"phone","email"})
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Doctor extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "doctor_seq",  // internal generator name
            sequenceName = "mediflow_schema.id_generator_seq", // DB sequence name
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true)
    @Setter(AccessLevel.NONE)
    private Long doctorCode;

    @Enumerated(EnumType.STRING)
    @ValidateEnum(enumClass = TitleEnum.class,message = "Invalid title value")
    @NotNull
    private TitleEnum title;

    @NotBlank
    @Size(min=2 ,max= 50)
    private String firstName;

    @NotBlank
    @Size(min=2 ,max = 50)
    private String lastName;


    @Enumerated(EnumType.STRING)
    @ValidateEnum(enumClass = SpecialtyEnum.class,message = "Invalid specialty value")
    @NotNull
    private SpecialtyEnum specialty;

    @NotBlank
    @Size(max=20)
    @Column(unique = true)
    @ValidatePhone(message = "Invalid phone format")
    private String phone;

    @Email
    @Size(max = 100)
    private String email;

    @PrePersist
    public void prePersist(){
        this.doctorCode =Long.parseLong(this.title.getValue()+this.specialty.getServiceCode());
    }
}
