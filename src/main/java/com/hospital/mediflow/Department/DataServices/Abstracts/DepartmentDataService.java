package com.hospital.mediflow.Department.DataServices.Abstracts;

import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentDataService {
    List<DepartmentResponseDto> findAllDepartments(DepartmentFilterDto filterDto);
    Page<DepartmentResponseDto> findAllDepartments(Pageable pageable,DepartmentFilterDto filterDto);
    DepartmentResponseDto findDepartmentById(Long id);
    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto);
    DepartmentResponseDto updateDepartment(Long id,DepartmentRequestDto departmentRequestDto);
    void deleteDepartment(Long id);
    DepartmentResponseDto addSpecialties(Long id, List<String> specialties);
    DepartmentResponseDto removeSpecialties(Long id, List<String> specialties);

}
