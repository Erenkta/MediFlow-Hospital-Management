package com.hospital.mediflow.Billing.Services.Abstracts;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingType;
import com.hospital.mediflow.Common.Dto.InvoicePdfProjection;
import com.hospital.mediflow.Common.Events.EventType;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
public interface BillingService {
    List<BillingResponseDto> findAllBillings(Predicate billingFilterDto);
    Page<BillingResponseDto> findAllBillings(Pageable pageable, Predicate billingFilterDto);
    BillingResponseDto findBillingById(@NotNull Long id);
    BillingResponseDto createBilling(@Valid BillingRequestDto billingRequestDto);
    Billing createBilling(Appointment appointment, BillingType billingType);
    Optional<BillingResponseDto> cancelBilling(Long appointmentId);
    BillingResponseDto updateBilling(@NotNull Long id,@Valid BillingRequestDto billingRequestDto);

    void notifyPatient(Long appointmentId, EventType type, Long userId, Map<String,Object> notifyParams);
    void deleteBilling(@NotNull Long id);

    List<InvoicePdfProjection> findBillingsByDateRanged(LocalDateTime startOfDay, LocalDateTime endOfDay);

    int markOverduePayments();

    List<Billing> getOverduePayments();
}
