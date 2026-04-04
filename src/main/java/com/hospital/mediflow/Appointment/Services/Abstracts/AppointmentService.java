package com.hospital.mediflow.Appointment.Services.Abstracts;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Common.Events.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface AppointmentService {
    List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto);
    Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto);
    AppointmentResponseDto findById(Long id);
    AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto);
    AppointmentResponseDto update(Long id,AppointmentRequestDto appointmentRequestDto);
    AppointmentResponseDto updateStatus(Long id, AppointmentStatusEnum newStatus);
    AppointmentResponseDto rescheduleAppointment(Long id, LocalDateTime newDate);
    List<LocalTime> getAvailableAppointmentDates(Long doctorId, LocalDate appointmentDate);
    void NotifyPatient(Long appointmentId,EventType type,Long userId,Map<String,Object> notifyParams);
    void NotifyPatient(Long appointmentId,EventType type,Long userId);
    List<Appointment> remindSoonAppointment(LocalDateTime remindDate);
    void deleteById(Long id);
}
