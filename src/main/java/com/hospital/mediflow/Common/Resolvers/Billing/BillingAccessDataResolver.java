package com.hospital.mediflow.Common.Resolvers.Billing;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Common.Annotations.AccessType;
import com.hospital.mediflow.Common.Aspects.BaseAspect;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;


@Component
public class BillingAccessDataResolver extends BaseAspect {

    public BillingAccessData resolve(JoinPoint jp, AccessType type) {
        switch (type) {
            case CREATE -> {
                BillingRequestDto dto = extract(jp, BillingRequestDto.class);
                return new BillingAccessData(null,dto.departmentId(), dto.appointmentId(), dto.patientId());
            }
            case READ_BY_FILTER -> {
                BillingFilterDto filter = extract(jp, BillingFilterDto.class);
                return new BillingAccessData(null,filter.departmentId(), filter.appointmentId(), filter.patientId());
            }
            case READ_BY_ID,DELETE->{
                Long billingId = extract(jp, Long.class);
                return new BillingAccessData(billingId,null,null,null);

            }
            case UPDATE -> {
                BillingRequestDto dto = extract(jp, BillingRequestDto.class);
                Long billingId = extract(jp, Long.class);
                return new BillingAccessData(billingId,dto.departmentId(),dto.appointmentId(),dto.patientId());
            }
            default -> throw new IllegalStateException("Billing Access Data couldn't resolved properly.");
        }
    }
}
