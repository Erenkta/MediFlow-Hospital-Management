package com.hospital.mediflow.Department.Domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record DepartmentRequestDto (
        @NotBlank(message = "Department name cannot be blank")
        String name,

        @NotBlank(message = "Department description cannot be blank")
        String description

){
}
