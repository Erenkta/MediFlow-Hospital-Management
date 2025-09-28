package com.hospital.mediflow.Doctor.Controllers;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService service;

    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody DoctorRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveDoctor(request));
    }

    @GetMapping
    public ResponseEntity<?> getDoctors(@Nullable Pageable pageable, DoctorFilterDto filter){
        return pageable == null
                ? ResponseEntity.status(HttpStatus.OK).body(service.findDoctors(filter))
                : ResponseEntity.status(HttpStatus.OK).body(service.findDoctors(pageable,filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findDoctorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable("id") Long id,@RequestBody DoctorRequestDto request){
       return ResponseEntity.status(HttpStatus.OK).body(service.updateDoctor(id,request));
    }
}
