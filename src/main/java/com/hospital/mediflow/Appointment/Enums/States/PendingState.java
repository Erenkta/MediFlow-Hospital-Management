package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Billing.Enums.BillingType;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Configuration.Properties.BillingProperties;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
public class PendingState extends AppointmentState{

    private final BillingService billingService;

    private final BillingProperties configuration;

    private final AppointmentService appointmentService;
    private final EntityManager entityManager;

    public PendingState(BillingService billingService, BillingProperties configuration, AppointmentService appointmentService, EntityManager entityManager) {
        this.billingService = billingService;
        this.configuration = configuration;
        this.appointmentService = appointmentService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void approve(Appointment appointment) {
        //Create a bill based on the configuration.
        appointment.setStatus(AppointmentStatusEnum.APPROVED);
        appointmentService.NotifyPatient(appointment.getId(), EventType.APPOINTMENT_APPROVED,appointment.getPatient().getId());
        billingService.createBilling(appointment, BillingType.DEPOSIT,configuration.getAmount());
//        entityManager.flush();
        billingService.notifyPatient(appointment.getId(),
                EventType.BILLING_DEPOSIT_CREATED,
                appointment.getPatient().getId(),
                Map.of(
                        "appointmentDate",appointment.getAppointmentDate().toString(),
                        "billingType",BillingType.DEPOSIT.name(),
                        "appointmentStatus",AppointmentStatusEnum.APPROVED.name()

                ));
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
