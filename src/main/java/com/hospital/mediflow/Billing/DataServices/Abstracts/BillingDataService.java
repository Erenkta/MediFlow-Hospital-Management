package com.hospital.mediflow.Billing.DataServices.Abstracts;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillingDataService {
    List<BillingResponseDto> findAllBillings(BillingFilterDto medicalRecordFilter);
    Page<BillingResponseDto> findAllBillings(Pageable pageable, BillingFilterDto medicalRecordFilter);
    BillingResponseDto findBillingById(Long id);
    BillingResponseDto createBilling(BillingRequestDto medicalRecord);
    BillingResponseDto updateBilling(Long id,BillingRequestDto medicalRecord);
    boolean isBillingDepartmentRelationExists(Long billingId,Long departmentId);
    void deleteBilling(Long id);
}
