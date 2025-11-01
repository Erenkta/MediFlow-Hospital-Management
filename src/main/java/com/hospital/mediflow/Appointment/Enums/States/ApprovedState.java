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

    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case DONE -> approve(appointment);
            case REJECTED -> reject(appointment);
            default -> throw new InvalidStatusTransitionException("Approved only can go to DONE or REJECTED");
        }
    }
}

