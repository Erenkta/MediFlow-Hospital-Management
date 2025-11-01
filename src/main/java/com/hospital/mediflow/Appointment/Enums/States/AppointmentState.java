package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;

public abstract class AppointmentState {
     public void approve(Appointment appointment) {
        throw new InvalidStatusTransitionException("Cannot approve from " + appointment.getStatus());
    }
     public void reject(Appointment appointment ) {
        appointment.setStatus(AppointmentStatusEnum.REJECTED);
    }

    public void rescheduled(Appointment appointment ) {
        throw new InvalidStatusTransitionException("Cannot reschedule from " + appointment.getStatus());
    }
    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus) {
        throw new InvalidStatusTransitionException("Cannot reschedule from " + appointment.getStatus());
    }

}
