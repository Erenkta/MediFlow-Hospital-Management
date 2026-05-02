package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingType;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationPipeline;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class PendingState extends AppointmentState{

    private final BillingService billingService;
    private final NotificationPipeline notificationPipeline;


    public PendingState(BillingService billingService, NotificationPipeline notificationPipeline) {
        this.billingService = billingService;
        this.notificationPipeline = notificationPipeline;
    }

    @Override
    @Transactional
    public void approve(Appointment appointment) {
        //Create a bill based on the configuration.
        appointment.setStatus(AppointmentStatusEnum.APPROVED);
        notificationPipeline.processAndNotify(appointment, ObjectType.APPOINTMENT,EventType.APPOINTMENT_APPROVED);

        Billing response =  billingService.createBilling(appointment, BillingType.DEPOSIT);
        notificationPipeline.processAndNotify(response,ObjectType.BILLING,EventType.BILLING_DEPOSIT_CREATED);

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
