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
    @Override
    public void cancel(Appointment appointment) {
        appointment.setStatus(AppointmentStatusEnum.CANCELLED);
    }
    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case APPROVED -> approve(appointment);
            case REJECTED -> reject(appointment);
            case CANCELLED -> cancel(appointment);
            default -> throw new InvalidStatusTransitionException("Pending only can go to APPROVED,CANCELLED or REJECTED");
        }
    }
}
