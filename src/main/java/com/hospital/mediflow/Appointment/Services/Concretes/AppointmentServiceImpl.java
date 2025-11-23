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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentDataService appointmentDataService;
    private final AppointmentMapper mapper;
    private final AppointmentProperties appointmentProperties;

    @Override
    public List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto) {
        return appointmentDataService.findAll(filterDto);
    }

    @Override
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto) {
        return appointmentDataService.findAll(pageable, filterDto);
    }

    @Override
    public AppointmentResponseDto findById(Long id) {
        return appointmentDataService.findById(id);
    }

    @Override
    @Transactional
    public AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto) {
        if(appointmentDataService.isAppointmentAvailable(
                appointmentRequestDto.doctorId(),
                appointmentRequestDto.appointmentDate()
        )){
            return appointmentDataService.save(appointmentRequestDto);
        }
        throw new AppointmentNotAvailableException("Selected appointment date is not available to select. Appointments must have at least 30 minutes between them.");
    }


    @Override
    public AppointmentResponseDto updateStatus(Long id,AppointmentStatusEnum newStatus){
        Appointment appointment = appointmentDataService.getReferenceById(id);
        if(Objects.nonNull(newStatus)){
            appointment.getState().handleTransition(appointment, newStatus);
        }
        return appointmentDataService.update(id, appointment);
    }
    @Override
    @Transactional
    public AppointmentResponseDto update(Long id, AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentDataService.getReferenceById(id);
        if(isAppointmentUpdatable(appointment)){
            mapper.updateEntity(appointment,appointmentRequestDto);
            return appointmentDataService.update(id, appointment);
        }
        throw new AppointmentNotAvailableException("An appointment cannot be updated within 1 hour before the appointment time.");

    }
    @Override
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
    public void deleteById(Long id) {
        appointmentDataService.deleteById(id);
    }
}
