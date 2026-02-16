package com.hospital.mediflow.Specialty.DataServices.Abstracts;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;

import java.util.List;

public interface SpecialtyDataService {
    SpecialtyResponseDto findSpecialtyByCode(String code);
    SpecialtyResponseDto updateSpecialty(String code,SpecialtyRequestDto requestDto);
    SpecialtyResponseDto createSpecialty(SpecialtyRequestDto requestDto);
    List<Specialty> assignDepartment(List<String> specialtyIds, Department department);
    List<Specialty> dismissDepartment(List<String> specialtyIds);
    boolean isSpecialtyDepartmentRelationExists(String specialtyCode,Long departmentId);
    List<SpecialtyResponseDto> findAllSpecialties();
    void deleteSpecialty(String code);

}
