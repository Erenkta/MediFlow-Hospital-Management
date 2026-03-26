package com.hospital.mediflow.Billing.DataServices.Abstracts;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Dto.InvoicePdfProjection;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BillingDataService {
    List<BillingResponseDto> findAllBillings(Predicate medicalRecordFilter);
    Page<BillingResponseDto> findAllBillings(Pageable pageable, Predicate medicalRecordFilter);
    BillingResponseDto findBillingById(Long id);
    BillingResponseDto createBilling(BillingRequestDto medicalRecord);
    BillingResponseDto updateBilling(Long id,BillingRequestDto medicalRecord);
    BillingResponseDto updateBillingStatus(Long billingId, BillingStatus status);
    boolean isBillingDepartmentRelationExists(Long billingId,Long departmentId);
    boolean isBillingPatientRelationExists(Long billingId,Long patientId);
    void deleteBilling(Long id);

    Optional<BillingResponseDto>  findBillingByAppointment(Long appointmentId);
    List<InvoicePdfProjection> findBillingsByDateRanged(LocalDateTime start,LocalDateTime end);
}
