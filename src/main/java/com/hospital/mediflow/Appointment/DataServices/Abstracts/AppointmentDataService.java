package com.hospital.mediflow.Appointment.DataServices.Abstracts;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDataService {
    List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto);
    Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto);
    AppointmentResponseDto findById(Long id);
    Appointment getReferenceById(Long id);
    AppointmentResponseDto save(AppointmentRequestDto patientResponseDto);
    boolean isAppointmentAvailable(Long doctorId, LocalDateTime appointmentDate);
    AppointmentResponseDto update(Long id, Appointment appointment);
    void deleteById(Long id);
}
