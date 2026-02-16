package com.hospital.mediflow.Common.Helpers.Predicate;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Entity.QBilling;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Patient.Domain.Entity.QPatient;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BillingPredicateBuilder {
    private final QBilling qBilling = QBilling.billing;
    private List<BooleanExpression> predicates = new ArrayList<>();

    public BillingPredicateBuilder withAmount(BigDecimal amountLessThan,BigDecimal amountGreaterThan) {
        if(amountGreaterThan != null){
            predicates.add(qBilling.amount.goe(amountGreaterThan));
        }
        if(amountLessThan != null){
            predicates.add(qBilling.amount.loe(amountLessThan));
        }
        return this;
    }
    public BillingPredicateBuilder withStatus(List<BillingStatus> status) {
        if(status != null){
            predicates.add(qBilling.status.in(status));
        }
        return this;
    }

    public BillingPredicateBuilder withBillingDate(LocalDateTime billingDateStart, LocalDateTime billingDateEnd) {
        if(billingDateStart != null){
            predicates.add(qBilling.billingDate.after(billingDateStart));
        }
        if(billingDateEnd != null){
            predicates.add(qBilling.billingDate.before(billingDateEnd));
        }
        return this;
    }
    public BillingPredicateBuilder withDepartmentId(Long departmentId){
        if(departmentId != null){
            predicates.add(qBilling.department.id.eq(departmentId));
        }
        return this;
    }
    public BillingPredicateBuilder withAppointmentId(Long appointmentId){
        if(appointmentId != null){
            predicates.add(qBilling.appointment.id.eq(appointmentId));
        }
        return this;
    }

    public Predicate build(BillingFilterDto filter){
       return this
                .withAmount(filter.amountLessThan(),filter.amountGreaterThan())
                .withBillingDate(filter.billingDateStart(),filter.billingDateEnd())
                .withStatus(filter.status())
                .withDepartmentId(filter.departmentId())
               .withAppointmentId(filter.appointmentId())
                .build();
    }

    public Predicate build(){
        return this.predicates.stream()
                .filter(Objects::nonNull)
                .reduce(BooleanExpression::and)
                .orElse(Expressions.TRUE);
    }
}
