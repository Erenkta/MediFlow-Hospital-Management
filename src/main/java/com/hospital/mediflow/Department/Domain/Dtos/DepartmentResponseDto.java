package com.hospital.mediflow.Department.Domain.Dtos;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;

import java.util.List;

public record DepartmentResponseDto(
        Long id,
        String name,
        String description,
        List<SpecialtyResponseDto> specialties
) {
}
