package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;

public class PendingState extends AppointmentState{
    @Override
    public void approve(Appointment appointment) {
        appointment.setStatus(AppointmentStatusEnum.APPROVED);
    }
    @Override
    public void rescheduled(Appointment appointment){
        appointment.setStatus(AppointmentStatusEnum.PENDING);
    }
    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case APPROVED -> approve(appointment);
            case REJECTED -> reject(appointment);
            default -> throw new InvalidStatusTransitionException("Pending only can go to APPROVED or REJECTED");
        }
    }
}
