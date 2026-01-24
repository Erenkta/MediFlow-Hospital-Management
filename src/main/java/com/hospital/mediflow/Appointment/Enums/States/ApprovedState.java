package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;

public class ApprovedState extends AppointmentState{

    @Override
    public void approve(Appointment appointment ) {
        appointment.setStatus(AppointmentStatusEnum.DONE);
    }
    @Override
    public void rescheduled(Appointment appointment){
        appointment.setStatus(AppointmentStatusEnum.PENDING);
    }
    @Override
    public void ongoing(Appointment appointment) {
        appointment.setStatus(AppointmentStatusEnum.APPROVED);
    }

    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case REJECTED -> reject(appointment);
            case ON_GOING -> ongoing(appointment);
            default -> throw new InvalidStatusTransitionException("Approved only can go to ON GOING or REJECTED");
        }
    }
}

