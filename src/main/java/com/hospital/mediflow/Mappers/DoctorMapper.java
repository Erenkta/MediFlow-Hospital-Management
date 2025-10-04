package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {

    default Optional<DoctorResponseDto> toDtoOptional(Doctor entity) {
        return entity == null ? Optional.empty() : Optional.of(toDto(entity));
    }

    default DoctorResponseDto toDto(Doctor entity){
        return DoctorResponseDto.builder()
                .id(entity.getId())
                .doctorCode(entity.getDoctorCode())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .specialty(entity.getSpecialty().getName())
                .title(entity.getTitle())
                .build();
    }
    @Mapping(target = "specialty", source = "requestDto.specialty", qualifiedByName = "specialtyCodeToEntity")
    Doctor toEntity(DoctorRequestDto requestDto);

    @Mapping(target = "specialty", source = "requestDto.specialty", qualifiedByName = "specialtyCodeToEntity")
    Doctor toUpdatedEntity(@MappingTarget Doctor entity, DoctorRequestDto requestDto);

    @Named("specialtyCodeToEntity")
    default Specialty mapSpecialty(String code) {
        if(code == null) return null;
        return Specialty.builder().code(code).build();
    }
}
