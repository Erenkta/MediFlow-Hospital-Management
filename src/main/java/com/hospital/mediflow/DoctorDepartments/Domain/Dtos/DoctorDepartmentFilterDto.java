package com.hospital.mediflow.DoctorDepartments.Domain.Dtos;

import java.util.List;

public record DoctorDepartmentFilterDto(
        String departmentName,
        String departmentDescription,
        List<String> specialties,
        List<Long> doctors,
        Integer departmentSize
) {
}
