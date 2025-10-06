package com.hospital.mediflow.Department.Domain.Entity;

import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Table(name = "departments",schema = "mediflow_schema")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Department extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "department_seq",
            sequenceName = "mediflow_schema.id_generator_seq",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_seq")
    private Long id;

    @NotBlank(message = "Department name cannot be blank")
    private String name;

    @NotBlank(message = "Department description cannot be blank")
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Specialty> specialties;
}
