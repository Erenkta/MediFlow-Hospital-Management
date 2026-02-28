package com.hospital.mediflow.Appointment.DataServices.Abstracts;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentDataService {
    List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto);
    Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto);
    AppointmentResponseDto findById(Long id);
    Appointment findByIdLocked(Long id);
    Appointment getReferenceById(Long id);
    AppointmentResponseDto save(AppointmentRequestDto patientResponseDto);
    AppointmentResponseDto saveAndFlush(AppointmentRequestDto patientResponseDto);
    boolean isAppointmentAvailable(Long doctorId, LocalDateTime appointmentDate);
    boolean isDepartmentAvailable(Long patientId,Long departmentId);
    boolean isAppointmentPatientRelationExists(Long appointmentId, Long patientId);
    boolean isAppointmentDoctorRelationExists(Long appointmentId, Long doctorId);
    boolean isAppointmentManagerRelationExists(Long appointmentId, Long departmentId);
    boolean isAppointmentExists(Long doctorId,Long patientId);
    List<LocalTime> getAvailableAppointmentDates(Long doctorId, LocalDateTime startDateTime,LocalDateTime endDateTime);
    AppointmentResponseDto update(Long id, Appointment appointment);
    void deleteById(Long id);
}
