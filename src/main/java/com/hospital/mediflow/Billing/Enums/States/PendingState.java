package com.hospital.mediflow.Billing.Enums.States;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PendingState extends BillingState {

    @Override
    public void approve(Billing billing) {
        billing.setStatus(BillingStatus.APPROVED);
    }
    @Override
    public void cancel(Billing billing) {
        billing.setStatus(BillingStatus.CANCELLED);
    }
    public void handleTransition(Billing billing,BillingStatus newStatus){
        switch (newStatus) {
            case APPROVED -> approve(billing);
            case CANCELLED -> cancel(billing);
            default -> throw new InvalidStatusTransitionException("Pending only can go to APPROVED or CANCELLED");
        }
    }
}
