package com.hospital.mediflow.Department.Services;

import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Services.Abstracts.DepartmentService;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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


    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN -> service.createDepartment(requestDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }


    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN -> service.updateDepartment(id, requestDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }



    public DepartmentResponseDto addSpecialties(Long id, List<String> specialties) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN -> service.addSpecialties(id, specialties);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }


    public DepartmentResponseDto removeSpecialties(Long id, List<String> specialties) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN -> service.removeSpecialties(id, specialties);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }


    public void deleteDepartment( Long id) {
        Role role = MediflowUserDetailsService.currentUserRole();
        switch (role) {
            case ADMIN ->service.deleteDepartment(id);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        }
    }
}
