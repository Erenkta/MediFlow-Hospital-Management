package com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts;

import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;

import java.util.List;

public interface DoctorDepartmentDataService {
    List<DoctorDepartmentResponseDto> findAll();
    void assignDoctorsToDepartment(List<Long> doctorIds, Long departmentId);
    void removeDoctorFromDepartment(Long doctorId, Long departmentId);
    DoctorDepartmentResponseDto findByDepartmentId(Long departmentId);
}
