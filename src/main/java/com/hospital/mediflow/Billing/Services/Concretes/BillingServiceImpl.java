package com.hospital.mediflow.Billing.Services.Concretes;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Configuration.Properties.BillingProperties;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingServiceImpl implements BillingService {
    private final BillingDataService dataService;
    private final BillingProperties configuration;

    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public List<BillingResponseDto> findAllBillings(Predicate billingFilterDto) {
        return dataService.findAllBillings(billingFilterDto);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public Page<BillingResponseDto> findAllBillings(Pageable pageable, Predicate billingFilterDto) {
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
    public BillingResponseDto createBilling(Appointment appointment, double amount) {
        BillingRequestDto requestDto = new BillingRequestDto(
                appointment.getPatient().getId(),
                appointment.getDoctor().getDoctorDepartment().stream().findFirst().get().getId().getDepartmentId(),
                appointment.getId(),
                BigDecimal.valueOf(amount),
                BillingStatus.PENDING,
                LocalDateTime.now()
        );
        return dataService.createBilling(requestDto);
    }

    @Override
    public BillingResponseDto cancelBilling(Long appointmentId) {
        BillingResponseDto response = dataService.findBillingByAppointment(appointmentId).orElseThrow(()->{
            String message = String.format("Billing with appointment id '%s' cannot found",appointmentId);
            return new RecordNotFoundException(message);
        });
        return dataService.updateBillingStatus(response.id(),BillingStatus.CANCELLED);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:update')")
    public BillingResponseDto updateBilling(Long id, BillingRequestDto billingRequest) {
        return dataService.updateBilling(id, billingRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('manager:delete')")
    public void deleteBilling(Long id) {
        dataService.deleteBilling(id);
    }
}
