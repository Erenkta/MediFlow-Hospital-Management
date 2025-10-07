package com.hospital.mediflow.Department.Services.Abstracts;

import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponseDto> findAllDepartments(DepartmentFilterDto filterDto);
    Page<DepartmentResponseDto> findAllDepartments(Pageable pageable,DepartmentFilterDto filterDto);
    DepartmentResponseDto findDepartmentById(@NotNull Long id);
    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto);
    DepartmentResponseDto updateDepartment(@NotNull Long id,DepartmentRequestDto departmentRequestDto);
    void deleteDepartment(@NotNull Long id);
    DepartmentResponseDto addSpecialties(@NotNull Long id, List<String> specialties);
    DepartmentResponseDto removeSpecialties(@NotNull Long id, List<String> specialties);
}

