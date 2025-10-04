package com.hospital.mediflow.Specialty.Domain.Dtos;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record SpecialtyRequestDto(
        @Size(min=3,max=3,message = "Code must be 3 digits")
        @NotBlank
        String code,

        @NotBlank(message = "Specialty name cannot be empty")
        String name

) { }
