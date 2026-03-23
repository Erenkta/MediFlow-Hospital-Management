package com.hospital.mediflow.Billing.Enums.States;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Exceptions.InvalidStatusTransitionException;

public abstract class BillingState {
     public void approve(Billing billing) {
        throw new InvalidStatusTransitionException("Cannot approve from " + billing.getStatus());
     }
     public void transfer(Billing billing ){
         throw new InvalidStatusTransitionException("Cannot transfer from " + billing.getStatus());
     }
     public void invoiced(Billing billing){
         throw new InvalidStatusTransitionException("Cannot invoince from " + billing.getStatus());
     }
     public void cancel(Billing billing){
         billing.setStatus(BillingStatus.CANCELLED);
     }
    public void handleTransition(Billing billing,BillingStatus newStatus) {
        throw new InvalidStatusTransitionException("Cannot reschedule from " + billing.getStatus());
    }
}
