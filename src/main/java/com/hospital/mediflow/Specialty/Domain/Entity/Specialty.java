package com.hospital.mediflow.Specialty.Domain.Entity;

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

}
