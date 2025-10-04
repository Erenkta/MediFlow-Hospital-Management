package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SpecialtyMapper {
    SpecialtyResponseDto toDto(Specialty specialty);
    Specialty toEntity(SpecialtyRequestDto requestDto);


    void toEntity(@MappingTarget Specialty specialty, SpecialtyRequestDto requestDto);
}
