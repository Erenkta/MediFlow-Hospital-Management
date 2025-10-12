package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorDepartmentMapper {
    @Mapping(target = "departmentId", source = "entity.id.departmentId")
    @Mapping(target = "departmentName", source = "entity.department.name")
    @Mapping(target = "departmentDescription", source = "entity.department.description")
    @Mapping(target = "doctors",source = "doctors")
    DoctorDepartmentResponseDto toDto(DoctorDepartment entity, List<DoctorResponseDto> doctors);

    @Mapping(target = "departmentId", source = "entity.id")
    @Mapping(target = "departmentName", source = "entity.name")
    @Mapping(target = "departmentDescription", source = "entity.description")
    @Mapping(target = "doctors",source = "doctors")
    DoctorDepartmentResponseDto toDto(Department entity, List<DoctorResponseDto> doctors);
}
