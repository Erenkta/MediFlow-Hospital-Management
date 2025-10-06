package com.hospital.mediflow.Department.Controller;

import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Services.Abstracts.DepartmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService service;

    @GetMapping
    public ResponseEntity<?> getDepartments(@NotNull Pageable pageable, DepartmentFilterDto filterDto){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(service.findAllDepartments(filterDto))
                : ResponseEntity.status(HttpStatus.OK).body(service.findAllDepartments(pageable,filterDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findDepartmentById(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDepartment(requestDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id,DepartmentRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateDepartment(id,requestDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id){
        service.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
