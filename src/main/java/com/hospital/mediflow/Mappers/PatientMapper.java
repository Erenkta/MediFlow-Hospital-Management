package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {
    PatientResponseDto toDto(Patient patient);
    Patient toEntity(PatientRequestDto requestDto);

    void updateEntity(@MappingTarget Patient patient, PatientRequestDto requestDto);
}
