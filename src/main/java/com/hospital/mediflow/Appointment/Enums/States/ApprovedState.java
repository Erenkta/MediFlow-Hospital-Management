package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApprovedState extends AppointmentState{

    private final BillingService billingService;

    public ApprovedState(BillingService billingService) {
        this.billingService = billingService;
    }

    @Override
    @Transactional
    public void rescheduled(Appointment appointment){
        billingService.cancelBilling(appointment.getId());
        appointment.setStatus(AppointmentStatusEnum.PENDING);
    }
    @Override
    public void ongoing(Appointment appointment) {
        appointment.setStatus(AppointmentStatusEnum.ON_GOING);
    }
    @Override
    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case REJECTED -> reject(appointment);
            case ON_GOING -> ongoing(appointment);
            default -> throw new InvalidStatusTransitionException("Approved only can go to ON GOING or REJECTED");
        }
    }
}

