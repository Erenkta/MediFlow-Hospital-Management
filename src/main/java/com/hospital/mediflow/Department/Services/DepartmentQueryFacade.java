package com.hospital.mediflow.Department.Services;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Services.Abstracts.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepartmentQueryFacade {
    private final DepartmentService service;

    public List<DepartmentResponseDto> getDepartments(DepartmentFilterDto filterDto) {
        return service.findAllDepartments(filterDto);
    }
    public Page<DepartmentResponseDto> getDepartments(Pageable pageable, DepartmentFilterDto filterDto) {
        return service.findAllDepartments(pageable, filterDto);
    }

    public DepartmentResponseDto getDepartmentById(Long id) {
        return service.findDepartmentById(id);
    }

    @ResourceAccess(
            resource = ResourceType.DEPARTMENT,
            action = AccessType.CREATE,
            payloadParam = "requestDto"
    )
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        return service.createDepartment(requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.DEPARTMENT,
            action = AccessType.UPDATE,
            idParam = "id",
            payloadParam = "requestDto"
    )
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto) {
        return service.updateDepartment(id, requestDto);
    }


    @ResourceAccess(
            resource = ResourceType.DEPARTMENT,
            action = AccessType.PATCH,
            idParam = "id",
            payloadParam = "specialties"
    )
    public DepartmentResponseDto addSpecialties(Long id, List<String> specialties) {
        return service.addSpecialties(id, specialties);
    }

    @ResourceAccess(
            resource = ResourceType.DEPARTMENT,
            action = AccessType.PATCH,
            idParam = "id",
            payloadParam = "specialties"
    )
    public DepartmentResponseDto removeSpecialties(Long id, List<String> specialties) {
        return service.removeSpecialties(id, specialties);
    }

    @ResourceAccess(
            resource = ResourceType.DEPARTMENT,
            action = AccessType.DELETE,
            idParam = "id"
    )
    public void deleteDepartment(Long id) {
        service.deleteDepartment(id);
    }
}
