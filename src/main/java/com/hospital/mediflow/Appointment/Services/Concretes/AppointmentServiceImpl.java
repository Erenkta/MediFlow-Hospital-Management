package com.hospital.mediflow.Appointment.Services.Concretes;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Common.Configuration.AppointmentProperties;
import com.hospital.mediflow.Common.Exceptions.AppointmentNotAvailableException;
import com.hospital.mediflow.Mappers.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentDataService appointmentDataService;
    private final AppointmentMapper mapper;
    private final AppointmentProperties appointmentProperties;

    @Override
    @PreAuthorize("hasAnyAuthority('doctor:read','patient:read')")
    public List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto) {
        return appointmentDataService.findAll(filterDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('doctor:read','patient:read')")
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto) {
        return appointmentDataService.findAll(pageable, filterDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('doctor:read','patient:read')")
    public AppointmentResponseDto findById(Long id) {
        return appointmentDataService.findById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('doctor:create','patient:create')")
    public AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto) {
        // Check if we can request an appointment to given department
        boolean isDepartmentAvailable = appointmentDataService.isDepartmentAvailable(appointmentRequestDto.patientId(),appointmentRequestDto.departmentId());
        // Check if requested appointment date is free.
        boolean isAppointmentAvailable = appointmentDataService.isAppointmentAvailable(
                appointmentRequestDto.doctorId(),
                appointmentRequestDto.appointmentDate()
        );
        if( isAppointmentAvailable && isDepartmentAvailable){
            try{
                return appointmentDataService.saveAndFlush(appointmentRequestDto);
            }catch (DataIntegrityViolationException ex){
                log.error("Appointment has already been occupied.");
                throw new AppointmentNotAvailableException("Appointment has already been occupied. Please try with another appointment time.");
            }
        }
        throw new AppointmentNotAvailableException("Selected appointment date is not available to select. Please check the appointment date and availability.");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('doctor:update','patient:update')")
    public AppointmentResponseDto updateStatus(Long id,AppointmentStatusEnum newStatus){
        Appointment appointment = appointmentDataService.findByIdLocked(id);
        appointment.getState().handleTransition(appointment, newStatus);
        return appointmentDataService.update(id, appointment);
    }
    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('doctor:update','patient:update')")
    public AppointmentResponseDto update(Long id, AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentDataService.getReferenceById(id);
        if(isAppointmentUpdatable(appointment)){
            mapper.updateEntity(appointment,appointmentRequestDto);
            return appointmentDataService.update(id, appointment);
        }
        throw new AppointmentNotAvailableException("An appointment cannot be updated within 1 hour before the appointment time.");

    }
    @Override
    @PreAuthorize("hasAnyAuthority('doctor:update','patient:update')")
    public AppointmentResponseDto rescheduleAppointment(Long id, LocalDateTime newDate) {
        Appointment appointment = appointmentDataService.getReferenceById(id);
        appointment.getState().rescheduled(appointment);
        appointment.setAppointmentDate(newDate);
        if(appointmentDataService.isAppointmentAvailable(
                appointment.getDoctor().getId(),
                appointment.getAppointmentDate()
        )){
            return appointmentDataService.update(id, appointment);
        }
        throw new AppointmentNotAvailableException("Selected appointment date is not available to reschedule. Appointments must have at least 30 minutes between them.");

    }
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('patient:read')")
    public List<LocalTime> getAvailableAppointmentDates(Long doctorId, LocalDate appointmentDate){
        LocalDateTime startDateTime = LocalDateTime.of(appointmentDate, appointmentProperties.getStartTime());
        LocalDateTime endDateTime = LocalDateTime.of(appointmentDate, appointmentProperties.getEndTime());

        return appointmentDataService.getAvailableAppointmentDates(doctorId,startDateTime,endDateTime);
    }

    private boolean isAppointmentUpdatable(Appointment appointment){
        return ((appointment.getStatus().equals(AppointmentStatusEnum.PENDING) && appointment.getVersion() == 0) ||
                (appointment.getStatus().equals(AppointmentStatusEnum.APPROVED) && appointment.getVersion() == 1)) &&
                LocalDateTime.now().plusHours(1).isBefore(appointment.getAppointmentDate());
    }
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('patient:delete')")
    public void deleteById(Long id) {
        appointmentDataService.deleteById(id);
    }
}
