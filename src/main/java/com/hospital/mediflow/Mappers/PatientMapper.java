package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import org.mapstruct.*;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {
    PatientResponseDto toDto(Patient patient);
    @Mapping(target = "bloodGroup",expression = "java(requestDto.bloodGroup().getValue())")
    Patient toEntity(PatientRequestDto requestDto);

    @Mapping(target = "bloodGroup",expression = "java(requestDto.bloodGroup().getValue())")
    void updateEntity(@MappingTarget Patient patient, PatientRequestDto requestDto);


}
