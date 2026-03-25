package com.hospital.mediflow.Billing.Enums.States;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;

public class ApprovedState extends BillingState {

    @Override
    public void transfer(Billing billing){
        billing.setStatus(BillingStatus.TRANSFERRED);
    }
    public void handleTransition(Billing billing,BillingStatus newStatus){
        switch (newStatus) {
            case  TRANSFERRED -> transfer(billing);
            case  CANCELLED -> cancel(billing);
            default -> throw new InvalidStatusTransitionException("Approved only can go to CANCELLED or TRANSFERRED");
        }
    }
}

