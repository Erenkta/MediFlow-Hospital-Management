package com.hospital.mediflow.Billing.Services.Concretes;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingServiceImpl implements BillingService {
    private final BillingDataService dataService;

    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public List<BillingResponseDto> findAllBillings(BillingFilterDto billingFilterDto) {
        return dataService.findAllBillings(billingFilterDto);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public Page<BillingResponseDto> findAllBillings(Pageable pageable, BillingFilterDto billingFilterDto) {
        return dataService.findAllBillings(pageable, billingFilterDto);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public BillingResponseDto findBillingById(Long id) {
        return dataService.findBillingById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:create')")
    public BillingResponseDto createBilling(BillingRequestDto billingRequest) {
        return dataService.createBilling(billingRequest);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:update')")
    public BillingResponseDto updateBilling(Long id, BillingRequestDto billingRequest) {
        return dataService.updateBilling(id, billingRequest);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:delete')")
    public void deleteBilling(Long id) {
        dataService.deleteBilling(id);
    }
}
