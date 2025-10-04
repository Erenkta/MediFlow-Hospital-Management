package com.hospital.mediflow.Specialty.Controller;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialties")
@RequiredArgsConstructor
public class SpecialtyController {
    private final SpecialtyService service;

    @PostMapping
    public ResponseEntity<SpecialtyResponseDto> createSpecialty(@RequestBody SpecialtyRequestDto request){
        return ResponseEntity.status(201).body(service.createSpecialty(request));
    }
    @GetMapping
    public ResponseEntity<List<SpecialtyResponseDto>> getSpecialties(){
        return ResponseEntity.status(200).body(service.findAllSpecialties());
    }
    @GetMapping("/{code}")
    public ResponseEntity<SpecialtyResponseDto> getSpecialtyByCode(@RequestParam(name = "code") String code){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findSpecialtyByCode(code));
    }
    @PutMapping("/{code}")
    public ResponseEntity<SpecialtyResponseDto> updateSpecialty(@RequestParam(name = "code") String code,@RequestBody SpecialtyRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateSpecialty(code,requestDto));
    }
}
