package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Services.Abstracts.DoctorDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerDoctorDepartmentQuery {
    private final DoctorDepartmentService service;

    @PreAuthorize("#departmentId == authentication.principal.resourceId")
    public DoctorDepartmentResponseDto signDoctorsToDepartment(List<Long> doctorList, Long departmentId) {
        return service.signDoctorsToDepartment(doctorList,departmentId);
    }

    @PreAuthorize("#departmentId == authentication.principal.resourceId")
    public DoctorDepartmentResponseDto removeDoctorFromDepartment(List<Long> doctorList, Long departmentId) {
        return service.removeDoctorFromDepartment(doctorList,departmentId);
    }
}
