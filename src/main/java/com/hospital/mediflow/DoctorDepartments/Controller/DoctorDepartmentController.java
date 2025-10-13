package com.hospital.mediflow.DoctorDepartments.Controller;

import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Services.Abstracts.DoctorDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor-department")
@RequiredArgsConstructor
public class DoctorDepartmentController {
    private final DoctorDepartmentService service;

    @GetMapping
    public ResponseEntity<List<DoctorDepartmentResponseDto>> findAll(DoctorDepartmentFilterDto filterDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(filterDto));
    }
    @PostMapping("/{department-id}")
    public ResponseEntity<DoctorDepartmentResponseDto> save(@PathVariable(name = "department-id") Long id, @RequestBody List<Long> doctorIds){
        return ResponseEntity.status(HttpStatus.OK).body(service.signDoctorsToDepartment(doctorIds,id));
    }
    @DeleteMapping("/{department-id}")
    public ResponseEntity<DoctorDepartmentResponseDto> delete(@PathVariable(name = "department-id") Long id,@RequestBody List<Long> doctorIds){
        return ResponseEntity.status(HttpStatus.OK).body(service.removeDoctorFromDepartment(doctorIds,id));
    }
}
