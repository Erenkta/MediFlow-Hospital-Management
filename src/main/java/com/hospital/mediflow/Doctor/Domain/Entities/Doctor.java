package com.hospital.mediflow.Doctor.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import com.hospital.mediflow.Common.Annotations.ValidatePhone;
import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


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


    @OneToOne
    @JoinColumn(name = "specialty", referencedColumnName = "code")
    private Specialty specialty;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private Set<DoctorDepartment> doctorDepartment = new HashSet<>();

    @NotBlank
    @Size(max=20)
    @Column(unique = true)
    @ValidatePhone(message = "Invalid phone format")
    private String phone;

    @Email
    @Size(max = 100)
    private String email;

    private Long generateDoctorCode(){
        return Long.parseLong(this.title.getValue()+this.specialty.getCode());
    }

    @Transient
    public String getFullName(){
        return firstName + " " + lastName;
    }

    @PrePersist
    public void prePersist(){
        this.doctorCode =generateDoctorCode();
    }

    @PreUpdate
    public void preUpdate(){
        this.doctorCode =generateDoctorCode();
    }
}
