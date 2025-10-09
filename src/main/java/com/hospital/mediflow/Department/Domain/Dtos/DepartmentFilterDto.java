package com.hospital.mediflow.Department.Domain.Dtos;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record DepartmentFilterDto(
        String name,
        String description,
        List<String> specialties
) {
}
