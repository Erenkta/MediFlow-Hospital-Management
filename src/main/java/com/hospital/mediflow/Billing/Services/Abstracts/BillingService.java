package com.hospital.mediflow.Billing.Services.Abstracts;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BillingService {
    List<BillingResponseDto> findAllBillings(Predicate billingFilterDto);
    Page<BillingResponseDto> findAllBillings(Pageable pageable, Predicate billingFilterDto);
    BillingResponseDto findBillingById(@NotNull Long id);
    BillingResponseDto createBilling(@Valid BillingRequestDto billingRequestDto);
    BillingResponseDto updateBilling(@NotNull Long id,@Valid BillingRequestDto billingRequestDto);
    void deleteBilling(@NotNull Long id);
}
