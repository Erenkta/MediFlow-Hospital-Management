package com.hospital.mediflow.Patient.Controller;

import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Services.Abstracts.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<? extends Iterable<PatientResponseDto>> findAll(Pageable pageable, PatientFilterDto filterDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                pageable.isUnpaged()
                        ? patientService.findAll(filterDto)
                        : patientService.findAll(pageable,filterDto)
        );
    }
    @GetMapping("/{patient-id}")
    public ResponseEntity<PatientResponseDto> findById(@PathVariable("patient-id") Long patientId){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findById(patientId));
    }
    @PostMapping
    public ResponseEntity<PatientResponseDto> save(@RequestBody @Valid PatientRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(requestDto));
    }
    @PutMapping("/{patient-id}")
    public ResponseEntity<PatientResponseDto> update(@PathVariable(name = "patient-id") Long id,@RequestBody PatientRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.update(id, requestDto));
    }
    @DeleteMapping("/{patient-id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "patient-id") Long id){
        patientService.deleteById(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
