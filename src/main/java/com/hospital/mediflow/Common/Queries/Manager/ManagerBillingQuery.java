package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerBillingAccess;
import com.hospital.mediflow.Common.Annotations.AccessType;
import com.hospital.mediflow.Common.Helpers.Predicate.BillingPredicateBuilder;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerBillingQuery {
    private final BillingService service;
    private final BillingPredicateBuilder filterBuilder;

    @ManagerBillingAccess(type = AccessType.READ_BY_FILTER)
    public List<BillingResponseDto> findAllBillings(BillingFilterDto filterDto) {
        Predicate filter = filterBuilder.build(filterDto);
        return service.findAllBillings(filter);
    }
    @ManagerBillingAccess(type = AccessType.READ_BY_FILTER)
    public Page<BillingResponseDto> findAllBillings(Pageable pageable, BillingFilterDto filterDto) {
        Predicate filter = filterBuilder.build(filterDto);
        return service.findAllBillings(pageable,filter);
    }
    @ManagerBillingAccess(type = AccessType.READ_BY_ID)
    public BillingResponseDto findBillingById(Long id) {
        return service.findBillingById(id);
    }

    @ManagerBillingAccess(type = AccessType.CREATE)
    public BillingResponseDto createBilling(BillingRequestDto requestDto) {
        return service.createBilling(requestDto);
    }

    @ManagerBillingAccess(type = AccessType.UPDATE)
    public BillingResponseDto updateBilling(Long id, BillingRequestDto requestDto) {
        return service.updateBilling(id, requestDto);
    }

    @ManagerBillingAccess(type = AccessType.DELETE)
    public void deleteBilling(Long id) {
        service.deleteBilling(id);
    }
}
