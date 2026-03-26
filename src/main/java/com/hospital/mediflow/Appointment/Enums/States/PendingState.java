package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Configuration.Properties.BillingProperties;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PendingState extends AppointmentState{

    @Autowired
    private  BillingService billingService;

    @Autowired
    private BillingProperties configuration;

    @Override
    @Transactional
    public void approve(Appointment appointment) {
        //Create a bill based on the configuration.
        billingService.createBilling(appointment,configuration.getAmount());
        appointment.setStatus(AppointmentStatusEnum.APPROVED);
    }
    @Override
    public void rescheduled(Appointment appointment){
        appointment.setStatus(AppointmentStatusEnum.PENDING);
    }

    @Override
    @Transactional
    public void cancel(Appointment appointment) {
        billingService.cancelBilling(appointment.getId());
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
