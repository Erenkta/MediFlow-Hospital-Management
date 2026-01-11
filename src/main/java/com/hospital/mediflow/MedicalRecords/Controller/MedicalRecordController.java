package com.hospital.mediflow.MedicalRecords.Controller;

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
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @GetMapping()
    public ResponseEntity<?> getDoctors(@NotNull Pageable pageable, MedicalRecordFilterDto filter){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.findAllMedicalRecords(filter))
                : ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.findAllMedicalRecords(pageable,filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecordResponseDto record = medicalRecordService.findMedicalRecordById(id);
        return ResponseEntity.ok(record);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MedicalRecordResponseDto> createMedicalRecord( @RequestBody @Valid MedicalRecordRequestDto requestDto) {
        MedicalRecordResponseDto created = medicalRecordService.createMedicalRecord(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> updateMedicalRecord(
            @PathVariable Long id,
            @RequestBody @Valid MedicalRecordRequestDto requestDto) {
        MedicalRecordResponseDto updated = medicalRecordService.updateMedicalRecord(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

}
