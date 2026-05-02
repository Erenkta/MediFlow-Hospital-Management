package com.hospital.mediflow.Common.Helpers.Notification;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;

public enum ObjectType {
    BILLING(Billing.class),
    APPOINTMENT(Appointment.class);

    private final Class<?> clazz;

    ObjectType(Class<?> clazz){
        this.clazz = clazz;
    }
    public Class<?> getClazz() {
        return clazz;
    }
}
