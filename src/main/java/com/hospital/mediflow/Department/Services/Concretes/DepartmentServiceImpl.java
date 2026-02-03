package com.hospital.mediflow.Department.Services.Concretes;

import com.hospital.mediflow.Department.DataServices.Abstracts.DepartmentDataService;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Services.Abstracts.DepartmentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDataService dataService;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('patient:read','doctor:read')")
    public List<DepartmentResponseDto> findAllDepartments(DepartmentFilterDto filterDto) {
        return dataService.findAllDepartments(filterDto);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('patient:read','doctor:read')")
    public Page<DepartmentResponseDto> findAllDepartments(Pageable pageable, DepartmentFilterDto filterDto) {
        return dataService.findAllDepartments(pageable,filterDto);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('patient:read','doctor:read')")
    public DepartmentResponseDto findDepartmentById(@NotNull Long id) {
        return dataService.findDepartmentById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:create')")
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {
        return dataService.createDepartment(departmentRequestDto);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:update')")
    public DepartmentResponseDto updateDepartment(@NotNull Long id, DepartmentRequestDto departmentRequestDto) {
        return dataService.updateDepartment(id,departmentRequestDto);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:delete')")
    public void deleteDepartment(@NotNull Long id) {
        dataService.deleteDepartment(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:patch')")
    public DepartmentResponseDto addSpecialties(@NotNull Long id, List<String> specialties) {
        return dataService.addSpecialties(id,specialties);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:patch')")
    public DepartmentResponseDto removeSpecialties(@NotNull Long id, List<String> specialties) {
        return dataService.removeSpecialties(id,specialties);
    }
}
