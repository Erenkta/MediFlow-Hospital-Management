package com.hospital.mediflow.Billing.Services;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.FilterManager;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Common.Helpers.Predicate.BillingPredicateBuilder;
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
public class BillingQueryFacade {
    private final BillingService billingService;
    private final BillingPredicateBuilder filterBuilder;


    @ResourceAccess(
            resource = ResourceType.BILLING,
            action = AccessType.READ_BY_FILTER,
            filterParam = "filterDto"
    )
    @FilterManager(
            resourceType = ResourceType.BILLING,
            filterClass = BillingFilterDto.class,
            filterParam = "filterDto"
    )
    public List<BillingResponseDto> getBillings(BillingFilterDto filterDto){
        Predicate filter = filterBuilder.build(filterDto);
        return billingService.findAllBillings(filter);
    }

    @ResourceAccess(
            resource = ResourceType.BILLING,
            action = AccessType.READ_BY_FILTER,
            filterParam = "filterDto"
    )
    @FilterManager(
            resourceType = ResourceType.BILLING,
            filterClass = BillingFilterDto.class,
            filterParam = "filterDto"
    )
    public Page<BillingResponseDto> getBillings(Pageable pageable, BillingFilterDto filterDto){
        Predicate filter = filterBuilder.build(filterDto);
        return billingService.findAllBillings(pageable,filter);
    }

    @ResourceAccess(
            resource = ResourceType.BILLING,
            action = AccessType.READ_BY_ID,
            idParam = "id"
    )
    public BillingResponseDto getBillingById(Long id) {
        return billingService.findBillingById(id);
    }

    @ResourceAccess(
            resource = ResourceType.BILLING,
            action = AccessType.CREATE,
            payloadParam = "requestDto"
    )
    public BillingResponseDto createBilling(BillingRequestDto requestDto) {
        return billingService.createBilling(requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.BILLING,
            action = AccessType.UPDATE,
            idParam = "id",
            payloadParam = "requestDto"
    )
    public BillingResponseDto updateBilling(Long id, BillingRequestDto requestDto) {
        return billingService.updateBilling(id, requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.BILLING,
            action = AccessType.DELETE,
            idParam = "id"
    )
    public void deleteBilling(Long id) {
        billingService.deleteBilling(id);
    }
}
