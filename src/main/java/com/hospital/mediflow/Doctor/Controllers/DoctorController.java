package com.hospital.mediflow.Doctor.Controllers;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<?> getDoctors(@NotNull Pageable pageable, DoctorFilterDto filter){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(service.findDoctors(filter))
                : ResponseEntity.status(HttpStatus.OK).body(service.findDoctors(pageable,filter));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getDoctorsByDoctorCode(@NotNull Pageable pageable, @RequestParam(value = "specialty",required = false) @Valid SpecialtyEnum specialty, @Valid @RequestParam(value = "title",required = false) TitleEnum title){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(service.findDoctorsByDoctorCode(specialty,title))
                : ResponseEntity.status(HttpStatus.OK).body(service.findDoctorsByDoctorCode(pageable,specialty,title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findDoctorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable("id") Long id,@RequestBody DoctorRequestDto request){
       return ResponseEntity.status(HttpStatus.OK).body(service.updateDoctor(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDoctor(@PathVariable("id") Long id){
        service.deleteDoctor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
