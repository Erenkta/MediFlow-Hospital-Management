package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingType;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Configuration.Properties.BillingProperties;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationPipeline;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnGoingState extends AppointmentState{

    private final BillingService billingService;
    private final NotificationPipeline notificationPipeline;


    @Override
    @Transactional
    public void approve(Appointment appointment) {
        appointment.setStatus(AppointmentStatusEnum.DONE);
        notificationPipeline.processAndNotify(appointment, ObjectType.APPOINTMENT,EventType.APPOINTMENT_DONE);
        Billing billing = billingService.createBilling(appointment,BillingType.TREATMENT);
        notificationPipeline.processAndNotify(billing,ObjectType.BILLING,EventType.BILLING_TREATMENT_CREATED);
    }

    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case DONE -> approve(appointment);
            default -> throw new InvalidStatusTransitionException("Approved only can go to DONE");
        }
    }
}

