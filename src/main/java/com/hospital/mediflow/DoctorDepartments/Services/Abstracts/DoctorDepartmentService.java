package com.hospital.mediflow.DoctorDepartments.Services.Abstracts;

import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorDepartmentService {
    List<DoctorDepartmentResponseDto> findAll();
    Page<DoctorDepartmentResponseDto> findAll(Pageable pageable);
    DoctorDepartmentResponseDto signDoctorsToDepartment(List<Long> doctorIds, Long departmentId);
    DoctorDepartmentResponseDto removeDoctorFromDepartment(List<Long> doctorIds,  Long departmentId);

}
