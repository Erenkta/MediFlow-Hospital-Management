package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {

    DoctorResponseDto toDto(Doctor entity);


    default Optional<DoctorResponseDto> toDtoOptional(Doctor entity) {
        return entity == null ? Optional.empty() : Optional.of(toDto(entity));
    }

    default Doctor toEntity(DoctorRequestDto requestDto){
        return  Doctor.builder()
                .firstName(requestDto.firstName())
                .lastName(requestDto.lastName())
                .phone(requestDto.phone())
                .email(requestDto.email())
                .title(requestDto.title())
                .specialty(requestDto.specialty())
                .doctorCode(Long.parseLong(requestDto.title()+requestDto.specialty().getServiceCode()))
                .build();
    }

    Doctor toUpdatedEntity(@MappingTarget Doctor entity, DoctorRequestDto requestDto);
}
