package com.hospital.mediflow.Department.Domain.Dtos;

import java.util.List;

public record DepartmentFilterDto(
        String name,
        String description,
        List<String> specialties
) {
}
