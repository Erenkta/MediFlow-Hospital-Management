package com.hospital.mediflow.Department.Domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record DepartmentRequestDto (
        @NotBlank(message = "Department name cannot be blank")
        String name,

        @NotBlank(message = "Department description cannot be blank")
        String description,

        List<String> specialties
){
}
