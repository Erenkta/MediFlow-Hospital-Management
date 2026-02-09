package com.hospital.mediflow.MedicalRecords.Controller;

import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.MedicalRecordQueryFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordController {
    private final MedicalRecordQueryFacade facade;

    @GetMapping()
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public ResponseEntity<?> getMedicalRecords(@NotNull Pageable pageable, MedicalRecordFilterDto filter){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(facade.getMedicalRecords(filter))
                : ResponseEntity.status(HttpStatus.OK).body(facade.getMedicalRecords(pageable,filter));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public ResponseEntity<MedicalRecordResponseDto> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecordResponseDto record = facade.getMedicalRecordById(id);
        return ResponseEntity.ok(record);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<MedicalRecordResponseDto> createMedicalRecord( @RequestBody @Valid MedicalRecordRequestDto requestDto) {
        MedicalRecordResponseDto created = facade.createMedicalRecord(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<MedicalRecordResponseDto> updateMedicalRecord(
            @PathVariable Long id,
            @RequestBody @Valid MedicalRecordRequestDto requestDto) {
        MedicalRecordResponseDto updated = facade.updateMedicalRecord(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER')")
    public void deleteMedicalRecord(@PathVariable Long id) {
        facade.deleteMedicalRecord(id);
    }

}
