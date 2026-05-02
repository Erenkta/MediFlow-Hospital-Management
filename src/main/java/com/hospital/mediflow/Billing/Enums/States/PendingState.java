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

@RequiredArgsConstructor
@Service("billingPendingState")
public class PendingState extends BillingState {
    private final NotificationPipeline notificationPipeline;

    @Override
    public void approve(Billing billing) {
        BillingStatus oldStatus = billing.getStatus();
        billing.setStatus(BillingStatus.APPROVED);
        notificationPipeline.processAndNotify(billing, ObjectType.BILLING, EventType.BILLING_STATUS_UPDATED, Map.of(
                "oldStatus",oldStatus,
                "newStatus",billing.getStatus().name(),
                "billingType", billing.getType().name(),
                "appointmentStatus", billing.getAppointment().getStatus().name()
        ));
    }
    @Override
    public void cancel(Billing billing) {
        BillingStatus oldStatus = billing.getStatus();
        billing.setStatus(BillingStatus.CANCELLED);
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
            case APPROVED -> approve(billing);
            case CANCELLED -> cancel(billing);
            default -> throw new InvalidStatusTransitionException("Pending only can go to APPROVED or CANCELLED");
        }
    }
}
