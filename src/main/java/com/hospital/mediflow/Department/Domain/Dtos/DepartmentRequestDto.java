package com.hospital.mediflow.Department.Domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record DepartmentRequestDto (
        @NotBlank(message = "Department name cannot be blank")
        String name,

        @NotBlank(message = "Department description cannot be blank")
        String description,

        List<
           @Size(max = 3,min = 3,message = "Specialty codes must be 3 digits")
           String
           > specialties
){
}
