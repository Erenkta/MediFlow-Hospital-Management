package com.hospital.mediflow.DoctorDepartments.Services;

import com.hospital.mediflow.Common.Queries.Manager.ManagerDoctorDepartmentQuery;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Services.Abstracts.DoctorDepartmentService;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DoctorDepartmentQueryFacade {
    private final DoctorDepartmentService service;
    private final ManagerDoctorDepartmentQuery managerQuery;

    public Page<DoctorDepartmentResponseDto> findAll(Pageable pageable, DoctorDepartmentFilterDto filterDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER   ->  service.findAll(pageable, filterDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public List<DoctorDepartmentResponseDto> findAll(DoctorDepartmentFilterDto filterDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER   ->  service.findAll(filterDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public DoctorDepartmentResponseDto signDoctorsToDepartment(Long id, Set<Long> doctorIds) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   ->  service.signDoctorsToDepartment(doctorIds.stream().toList(), id);
            case MANAGER -> managerQuery.signDoctorsToDepartment(doctorIds.stream().toList(),id);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public DoctorDepartmentResponseDto removeDoctorFromDepartment(Long id, Set<Long> doctorIds) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> service.removeDoctorFromDepartment(doctorIds.stream().toList(), id);
            case MANAGER -> managerQuery.removeDoctorFromDepartment(doctorIds.stream().toList(),id);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }
}
