package com.hospital.mediflow.Billing.Enums.States;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationPipeline;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("billingApprovedState")
@RequiredArgsConstructor
public class ApprovedState extends BillingState {
    private final NotificationPipeline notificationPipeline;

    @Override
    public void transfer(Billing billing){
        BillingStatus oldStatus = billing.getStatus();
        billing.setStatus(BillingStatus.TRANSFERRED);
        notificationPipeline.processAndNotify(billing, ObjectType.BILLING, EventType.BILLING_STATUS_UPDATED, Map.of(
                "oldStatus",oldStatus,
                "newStatus",billing.getStatus().name(),
                "billingType", billing.getType().name(),
                "appointmentStatus", billing.getAppointment().getStatus().name()
        ));
    }

    @Override
    public void handleTransition(Billing billing,BillingStatus newStatus){
        switch (newStatus) {
            case  TRANSFERRED -> transfer(billing);
            case  CANCELLED -> cancel(billing);
            default -> throw new InvalidStatusTransitionException("Approved only can go to CANCELLED or TRANSFERRED");
        }
    }
}

