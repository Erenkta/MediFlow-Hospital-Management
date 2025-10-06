package com.hospital.mediflow.Specialty.Domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record SpecialtyRequestDto(
        @NotBlank(message = "Specialty name cannot be empty")
        String name

) { }
