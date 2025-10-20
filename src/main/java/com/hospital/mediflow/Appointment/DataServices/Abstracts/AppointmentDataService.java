package com.hospital.mediflow.Appointment.DataServices.Abstracts;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentDataService {
    List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto);
    Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto);
    AppointmentResponseDto findById(Long id);
    AppointmentResponseDto save(AppointmentRequestDto patientResponseDto);
    AppointmentResponseDto update(Long id,AppointmentRequestDto patientResponseDto);
    void deleteById(Long id);
}
