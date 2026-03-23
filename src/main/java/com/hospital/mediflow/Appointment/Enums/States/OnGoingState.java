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
public class OnGoingState extends AppointmentState{

    @Autowired
    private BillingService billingService;

    @Autowired
    private BillingProperties configuration;

    @Override
    @Transactional
    public void approve(Appointment appointment) {
        billingService.createBilling(appointment,configuration.getRemainedAmount());
        appointment.setStatus(AppointmentStatusEnum.DONE);
    }

    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case DONE -> approve(appointment);
            default -> throw new InvalidStatusTransitionException("Approved only can go to DONE");
        }
    }
}

