package com.hospital.mediflow.DoctorDepartments.Services;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
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

    public Page<DoctorDepartmentResponseDto> findAll(Pageable pageable, DoctorDepartmentFilterDto filterDto) {
        return service.findAll(pageable, filterDto);
    }

    public List<DoctorDepartmentResponseDto> findAll(DoctorDepartmentFilterDto filterDto) {
        return service.findAll(filterDto);
    }

    @ResourceAccess(
            resource = ResourceType.DOCTOR_DEPARTMENT,
            action = AccessType.UPDATE,
            idParam = "id",
            payloadParam = "doctorIds"
    )
    public DoctorDepartmentResponseDto signDoctorsToDepartment(Long id, Set<Long> doctorIds) {
        return service.signDoctorsToDepartment(doctorIds.stream().toList(), id);
    }

    @ResourceAccess(
            resource = ResourceType.DOCTOR_DEPARTMENT,
            action = AccessType.DELETE,
            idParam = "id",
            payloadParam = "doctorIds"
    )
    public DoctorDepartmentResponseDto removeDoctorFromDepartment(Long id, Set<Long> doctorIds) {
        return service.removeDoctorFromDepartment(doctorIds.stream().toList(), id);
    }
}
