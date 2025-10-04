package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SpecialtyMapper {
    SpecialtyDto  toDto(Specialty specialty);
}
