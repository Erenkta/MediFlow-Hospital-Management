package com.hospital.mediflow.DoctorDepartments.Domain.Dtos;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;

import java.util.List;

public record DoctorDepartmentResponseDto(
        Long departmentId,
        String departmentName,
        String departmentDescription,
        List<DoctorResponseDto> doctors
) {
}
