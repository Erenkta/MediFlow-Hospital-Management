package com.hospital.mediflow.Billing.Enums.States;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;
import org.springframework.stereotype.Service;

@Service("billingCancelledState")
public class CancelledState extends BillingState {

    @Override
    public void handleTransition(Billing billing, BillingStatus newStatus){
        throw new InvalidStatusTransitionException("Cancelled billing cannot be updated.");
    }
}
