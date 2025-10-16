package com.hospital.mediflow.Specialty.Domain.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record SpecialtyResponseDto(
        @Schema(description = "Unique code of the specialty", example = "000")
        String code,
        @Schema(description = "Name of the specialty", example = "Cardiology")
        String name
) {
}
