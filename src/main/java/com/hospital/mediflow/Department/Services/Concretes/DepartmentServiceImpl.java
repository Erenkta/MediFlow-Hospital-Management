package com.hospital.mediflow.Department.Services.Concretes;

import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Department.DataServices.Abstracts.DepartmentDataService;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Department.Services.Abstracts.DepartmentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDataService dataService;

    @Override
    public List<DepartmentResponseDto> findAllDepartments(DepartmentFilterDto filterDto) {
        return dataService.findAllDepartments(filterDto);
    }

    @Override
    public Page<DepartmentResponseDto> findAllDepartments(Pageable pageable, DepartmentFilterDto filterDto) {
        return dataService.findAllDepartments(pageable,filterDto);
    }

    @Override
    public DepartmentResponseDto findDepartmentById(@NotNull Long id) {
        return dataService.findDepartmentById(id);
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {
        return dataService.createDepartment(departmentRequestDto);
    }

    @Override
    public DepartmentResponseDto updateDepartment(@NotNull Long id, DepartmentRequestDto departmentRequestDto) {
        return dataService.updateDepartment(id,departmentRequestDto);
    }

    @Override
    public void deleteDepartment(@NotNull Long id) {
        dataService.deleteDepartment(id);
    }

    @Override
    public DepartmentResponseDto addSpecialties(@NotNull Long id, List<String> specialties) {
        return dataService.addSpecialties(id,specialties);
    }

    @Override
    public DepartmentResponseDto removeSpecialties(@NotNull Long id, List<String> specialties) {
        return dataService.removeSpecialties(id,specialties);
    }
}
