package com.hospital.mediflow.Common.Helpers.Schedulers;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationPipeline;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {
    private final AppointmentService appointmentService;
    private final NotificationPipeline notificationPipeline;

    @Scheduled(fixedRate = 30 * 60 * 1000)
    @Transactional
    public void appointmentReminder() {
        LocalDateTime remindDate = LocalDateTime.now().plusMinutes(30);
        List<Appointment> appointments = appointmentService.remindSoonAppointment(remindDate);
        appointments.parallelStream().forEach(appointment -> notificationPipeline.processAndNotify(appointment, ObjectType.APPOINTMENT,EventType.APPOINTMENT_SOON));
    }
}
