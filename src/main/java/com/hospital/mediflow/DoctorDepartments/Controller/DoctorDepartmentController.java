package com.hospital.mediflow.DoctorDepartments.Controller;

import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Services.Abstracts.DoctorDepartmentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/doctor-department")
@RequiredArgsConstructor
public class DoctorDepartmentController {
    private final DoctorDepartmentService service;

    @GetMapping
    public ResponseEntity<?> findAll(@NotNull Pageable pageable ,DoctorDepartmentFilterDto filterDto){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(service.findAll(filterDto))
                : ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable,filterDto));
    }
    @PostMapping("/{department-id}")
    public ResponseEntity<DoctorDepartmentResponseDto> save(@PathVariable(name = "department-id") Long id, @RequestBody Set<Long> doctorIds){
        return ResponseEntity.status(HttpStatus.OK).body(service.signDoctorsToDepartment(doctorIds.stream().toList(),id));
    }
    @DeleteMapping("/{department-id}")
    public ResponseEntity<DoctorDepartmentResponseDto> delete(@PathVariable(name = "department-id") Long id,@RequestBody List<Long> doctorIds){
        return ResponseEntity.status(HttpStatus.OK).body(service.removeDoctorFromDepartment(doctorIds,id));
    }
}
