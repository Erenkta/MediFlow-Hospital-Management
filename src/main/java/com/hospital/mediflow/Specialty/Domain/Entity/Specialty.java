package com.hospital.mediflow.Specialty.Domain.Entity;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "specialties",schema = "mediflow_schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Specialty implements Serializable {

    @Id
    private String code;

    @NotBlank(message = "Specialty name cannot be empty")
    private String name;

    public void createCode(Integer number){
        this.code = String.format("%03d",number+1);
    }

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
