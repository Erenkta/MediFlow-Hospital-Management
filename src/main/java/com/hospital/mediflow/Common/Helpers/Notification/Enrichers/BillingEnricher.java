package com.hospital.mediflow.Common.Helpers.Notification.Enrichers;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationContext;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import org.springframework.stereotype.Service;


@Service
public class BillingEnricher implements NotificationEnricher {
    @Override
    public boolean supports(NotificationContext context) {
        return context.getObjectType().equals(ObjectType.BILLING);
    }

    @Override
    public void enrich(NotificationContext context) {
        Billing billing = (Billing) context.getEntity();
        //Default Billing Notification Values

        context.getData().putIfAbsent("billingDate",billing.getBillingDate().toString());
        context.getData().putIfAbsent("paymentDate",billing.getPaymentDate().toString());
        context.getData().putIfAbsent("amount",billing.getAmount());
        context.getData().putIfAbsent("appointmentDate",billing.getAppointment().getAppointmentDate().toString());

    }
}
