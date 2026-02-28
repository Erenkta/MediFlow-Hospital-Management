package com.hospital.mediflow.Appointment.Services;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
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
    private final CurrentUserProvider userProvider;


    public  List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto){
        Role role = MediflowUserDetailsService.currentUserRole();
        Long resourceId = userProvider.get().getResourceId();
        return switch (role) {
            case ADMIN   -> appointmentService.findAll(filterDto);
            case MANAGER -> appointmentService.findAll(filterDto.ManagerFilter(resourceId));
            case DOCTOR  -> appointmentService.findAll(filterDto.DoctorFilter(resourceId));
            case PATIENT -> appointmentService.findAll(filterDto.PatientFilter(resourceId));

        };
    }
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto){
        Role role = MediflowUserDetailsService.currentUserRole();
        Long resourceId = userProvider.get().getResourceId();
        return switch (role) {
            case ADMIN   -> appointmentService.findAll(pageable,filterDto);
            case MANAGER -> appointmentService.findAll(pageable,filterDto.ManagerFilter(resourceId));
            case DOCTOR  -> appointmentService.findAll(pageable,filterDto.DoctorFilter(resourceId));
            case PATIENT -> appointmentService.findAll(pageable,filterDto.PatientFilter(resourceId));
        };
    }

    public AppointmentResponseDto findById(Long id){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR,MANAGER,PATIENT,ADMIN  -> appointmentService.findById(id);
        };
    }

    public List<LocalTime> findAvailableAppointments(Long doctorId,LocalDate appointmentDate){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR,MANAGER,PATIENT,ADMIN  -> appointmentService.getAvailableAppointmentDates(doctorId,appointmentDate);
        };
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
