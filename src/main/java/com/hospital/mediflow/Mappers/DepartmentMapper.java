package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import org.mapstruct.*;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,uses = {SpecialtyMapper.class})
public interface DepartmentMapper {

    DepartmentResponseDto toDto(Department department);

    @Mapping(target = "specialties",ignore = true)
    Department toEntity(DepartmentRequestDto departmentRequestDto);

    @Mapping(target = "specialties",ignore = true)
    void toEntity(@MappingTarget Department entity,DepartmentRequestDto requestDto);
}
