package com.hospital.mediflow.Specialty.Domain.Entity;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specialties",schema = "mediflow_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Specialty {

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
