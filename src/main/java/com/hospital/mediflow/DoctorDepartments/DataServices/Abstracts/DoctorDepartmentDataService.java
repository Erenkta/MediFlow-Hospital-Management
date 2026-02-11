package com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts;

import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorDepartmentDataService {
    List<DoctorDepartmentResponseDto> findAll(DoctorDepartmentFilterDto filterDto);
    Page<DoctorDepartmentResponseDto> findAll(Pageable pageable, DoctorDepartmentFilterDto filterDto);
    boolean isDepartmentDoctorRelationsExists(Long doctorId, Long departmentId);
    void assignDoctorsToDepartment(List<Long> doctorIds, Long departmentId);
    void removeDoctorFromDepartment(Long doctorId, Long departmentId);
    DoctorDepartmentResponseDto findByDepartmentId(Long departmentId);
}
