package com.hospital.mediflow.Billing.Enums.States;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;

public class TransferredState extends BillingState{
    @Override
    public void invoiced(Billing billing) {
        //download the billing as pdf (?)
        billing.setStatus(BillingStatus.APPROVED);
    }

    public void handleTransition(Billing billing,BillingStatus newStatus){
        switch (newStatus) {
            case INVOICED -> approve(billing);
            default -> throw new InvalidStatusTransitionException("Transferred billing only can go to Invoiced");
        }
    }
}
