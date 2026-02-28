package com.hospital.mediflow.Appointment.Services;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.FilterManager;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentQueryFacade {
    private final AppointmentService appointmentService;


    @FilterManager(
            resourceType = ResourceType.APPOINTMENT,
            filterClass = AppointmentFilterDto.class,
            filterParam = "filterDto"
    )
    public  List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto){
        return appointmentService.findAll(filterDto);
    }
    @FilterManager(
            resourceType = ResourceType.APPOINTMENT,
            filterClass = AppointmentFilterDto.class,
            filterParam = "filterDto"
    )
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto){
       return appointmentService.findAll(pageable,filterDto);
    }

    public AppointmentResponseDto findById(Long id){
        return appointmentService.findById(id);
    }

    public List<LocalTime> findAvailableAppointments(Long doctorId,LocalDate appointmentDate){
        return appointmentService.getAvailableAppointmentDates(doctorId,appointmentDate);
    }

    @ResourceAccess(
            resource = ResourceType.APPOINTMENT,
            action = AccessType.CREATE,
            payloadParam = "requestDto"
    )
    public AppointmentResponseDto save(AppointmentRequestDto requestDto){
        return appointmentService.save(requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.APPOINTMENT,
            action = AccessType.UPDATE,
            idParam = "id",
            payloadParam = "requestDto"
    )
    public AppointmentResponseDto update(Long id,AppointmentRequestDto requestDto){
        return appointmentService.update(id, requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.APPOINTMENT,
            action = AccessType.PATCH,
            idParam = "id"
    )
    public AppointmentResponseDto updateStatus(Long id,AppointmentStatusEnum newStatus){
        return appointmentService.updateStatus(id, newStatus);
    }

    @ResourceAccess(
            resource = ResourceType.APPOINTMENT,
            action = AccessType.RESCHEDULE,
            idParam = "id"
    )
    public AppointmentResponseDto rescheduleAppointment(Long id,LocalDateTime newDate){
        return appointmentService.rescheduleAppointment(id, newDate);
    }

    @ResourceAccess(
            resource = ResourceType.APPOINTMENT,
            action = AccessType.DELETE,
            idParam = "id"
    )
    public void delete(Long id){
        appointmentService.deleteById(id);
    }
}
