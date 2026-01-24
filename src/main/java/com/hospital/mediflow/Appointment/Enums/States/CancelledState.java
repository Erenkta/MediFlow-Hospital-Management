package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;

public class CancelledState extends AppointmentState{
    @Override
    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        throw new InvalidStatusTransitionException("Cancelled appointments cannot be updated.");
    }
}
