package com.hospital.mediflow.Appointment.Enums.States;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Billing.Enums.BillingType;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Configuration.Properties.BillingProperties;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
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


    private final AppointmentService appointmentService;


    @Override
    @Transactional
    public void approve(Appointment appointment) {
        appointment.setStatus(AppointmentStatusEnum.DONE);
        appointmentService.NotifyPatient(appointment.getId(), EventType.APPOINTMENT_DONE,appointment.getPatient().getId());
        billingService.createBilling(appointment,BillingType.TREATMENT);
        billingService.notifyPatient(appointment.getId(),
                EventType.BILLING_TREATMENT_CREATED,
                appointment.getPatient().getId(),
                Map.of(
                        "appointmentDate",appointment.getAppointmentDate().toString(),
                        "billingType",BillingType.TREATMENT.name(),
                        "appointmentStatus",AppointmentStatusEnum.DONE.name()
                ));
    }

    public void handleTransition(Appointment appointment,AppointmentStatusEnum newStatus){
        switch (newStatus) {
            case DONE -> approve(appointment);
            default -> throw new InvalidStatusTransitionException("Approved only can go to DONE");
        }
    }
}

