package com.hospital.mediflow.DoctorDepartments.Domain.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DoctorDepartmentFilterDto(
        String departmentName,
        String departmentDescription,
        List<String> specialties,
        List<Long> doctors,
        @JsonProperty(value = "departmentSize", defaultValue = "0")
        Integer departmentSize
) {
        public DoctorDepartmentFilterDto {
                if (departmentSize == null) {
                        departmentSize = 0; // default
                }
        }
}