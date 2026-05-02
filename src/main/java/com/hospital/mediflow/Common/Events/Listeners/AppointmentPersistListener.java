package com.hospital.mediflow.Common.Events.Listeners;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationPipeline;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppointmentPersistListener {
    private static NotificationPipeline notificationPipeline;
    @Autowired
    public void setNotificationPipeline(NotificationPipeline pipeline) {
        notificationPipeline = pipeline;
    }
    @PostPersist
    public void handleAppointmentCreated(Appointment entity){
        log.info("Appointment notification started");
        notificationPipeline.processAndNotify(entity, ObjectType.APPOINTMENT, EventType.APPOINTMENT_CREATED);
        log.info("Appointment notification finished");
    }
}
