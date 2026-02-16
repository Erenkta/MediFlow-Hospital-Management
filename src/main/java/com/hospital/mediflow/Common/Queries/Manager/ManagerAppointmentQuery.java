package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerAppointmentAccess;
import com.hospital.mediflow.Common.Annotations.AccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerAppointmentQuery {
    private final AppointmentService service;
    @ManagerAppointmentAccess(type = AccessType.PATCH)
    public AppointmentResponseDto updateStatus(Long id, AppointmentStatusEnum newStatus) {
        return service.updateStatus(id, newStatus);
    }

    @ManagerAppointmentAccess(type = AccessType.PATCH)
    public AppointmentResponseDto rescheduleAppointment(Long id, LocalDateTime newDate) {
        return service.rescheduleAppointment(id, newDate);

    }
    @ManagerAppointmentAccess(type = AccessType.DELETE)
    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
