package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {
    AppointmentResponseDto toDto(Appointment appointment);
    Appointment toEntity(AppointmentRequestDto requestDto);
    void updateEntity(@MappingTarget Appointment appointment, AppointmentRequestDto requestDto);
}
