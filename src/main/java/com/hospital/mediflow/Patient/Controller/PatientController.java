package com.hospital.mediflow.Patient.Controller;

import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Services.Abstracts.PatientService;
import com.hospital.mediflow.Patient.Services.PatientQueryFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('DOCTOR','PATIENT')")
public class PatientController {
    private final PatientService patientService;
    private final PatientQueryFacade facade;

    @GetMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<? extends Iterable<PatientResponseDto>> findAll(Pageable pageable, PatientFilterDto filterDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                pageable.isUnpaged()
                        ? facade.findPatient(filterDto)
                        : facade.findPatient(pageable,filterDto)
        );
    }
    @GetMapping("/{patient-id}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public ResponseEntity<PatientResponseDto> findById(@PathVariable("patient-id") Long patientId){
        return ResponseEntity.status(HttpStatus.OK).body(facade.findPatientById(patientId));
    }
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<PatientResponseDto> save(@RequestBody @Valid PatientRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(requestDto));
    }
    @PutMapping("/{patient-id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponseDto> update(@PathVariable(name = "patient-id") Long id,@RequestBody PatientRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(facade.updatePatient(id, requestDto));
    }
    @DeleteMapping("/{patient-id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Void> delete(@PathVariable(name = "patient-id") Long id){
        facade.deletePatient(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
