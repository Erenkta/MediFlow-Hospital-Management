package com.hospital.mediflow.Billing.Controllers;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/billings")
public class BillingController {

    private final BillingService billingService;

    @GetMapping()
    public ResponseEntity<?> getDoctors(@NotNull Pageable pageable, BillingFilterDto filter){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(billingService.findAllBillings(filter))
                : ResponseEntity.status(HttpStatus.OK).body(billingService.findAllBillings(pageable,filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingResponseDto> getMedicalRecordById(@PathVariable Long id) {
        BillingResponseDto record = billingService.findBillingById(id);
        return ResponseEntity.ok(record);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BillingResponseDto> createMedicalRecord( @RequestBody @Valid BillingRequestDto requestDto) {
        BillingResponseDto created = billingService.createBilling(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillingResponseDto> updateMedicalRecord(
            @PathVariable Long id,
            @RequestBody @Valid BillingRequestDto requestDto) {
        BillingResponseDto updated = billingService.updateBilling(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedicalRecord(@PathVariable Long id) {
        billingService.deleteBilling(id);
    }

}
