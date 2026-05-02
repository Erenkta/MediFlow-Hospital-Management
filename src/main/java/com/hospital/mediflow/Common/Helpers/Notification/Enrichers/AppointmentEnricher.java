package com.hospital.mediflow.Common.Helpers.Notification.Enrichers;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationContext;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import org.springframework.stereotype.Service;

@Service

public class AppointmentEnricher implements NotificationEnricher{
    @Override
    public boolean supports(NotificationContext context) {
        return context.getObjectType().equals(ObjectType.APPOINTMENT);
    }

    @Override
    public void enrich(NotificationContext context) {
        Appointment appointment = (Appointment)context.getEntity();

        //Default Appointment Notification Values

        context.getData().putIfAbsent("doctorName",appointment.getDoctor().getFullName());
        context.getData().putIfAbsent("departmentName",appointment.getDoctor().getDoctorDepartment().stream().findFirst().get().getDepartment().getName());
        context.getData().putIfAbsent("date",appointment.getAppointmentDate().toString());
    }
}
