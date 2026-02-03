package com.hospital.mediflow.Department.Controller;

import com.hospital.mediflow.Common.Exceptions.ErrorResponse;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Services.Abstracts.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @Operation(summary = "Get departments", description = "Returns all departments, pageable or unpaged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departments retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT')")
    public ResponseEntity<? extends Iterable<DepartmentResponseDto>> getDepartments(
            @Parameter(description = "Pageable info for pagination", required = true)
            @NotNull Pageable pageable,
            @Parameter(description = "Filter criteria for departments")
            DepartmentFilterDto filterDto) {

        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(service.findAllDepartments(filterDto))
                : ResponseEntity.status(HttpStatus.OK).body(service.findAllDepartments(pageable, filterDto));
    }

    @Operation(summary = "Get department by ID", description = "Retrieve a single department by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Department found",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT')")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findDepartmentById(id));
    }

    @Operation(summary = "Create a department", description = "Creates a new department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Department created successfully",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<DepartmentResponseDto> createDepartment(
            @Parameter(description = "Department data to create", required = true)
            @Valid @RequestBody DepartmentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDepartment(requestDto));
    }

    @Operation(summary = "Update a department", description = "Updates an existing department by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department updated successfully",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(
            @Parameter(description = "ID of the department to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated department data", required = true)
            @RequestBody DepartmentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateDepartment(id, requestDto));
    }

    @Operation(summary = "Add specialties to a department", description = "Adds one or more specialties to a department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specialties added successfully",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/add-specialties")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<DepartmentResponseDto> addSpecialties(
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long id,
            @Parameter(description = "List of specialty codes to add", required = true)
            @RequestBody List<String> specialties) {
        return ResponseEntity.status(HttpStatus.OK).body(service.addSpecialties(id, specialties));
    }

    @Operation(summary = "Remove specialties from a department", description = "Removes one or more specialties from a department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specialties removed successfully",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/remove-specialties")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<DepartmentResponseDto> removeSpecialties(
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long id,
            @Parameter(description = "List of specialty codes to remove", required = true)
            @RequestBody List<String> specialties) {
        return ResponseEntity.status(HttpStatus.OK).body(service.removeSpecialties(id, specialties));
    }

    @Operation(summary = "Delete a department", description = "Deletes a department by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteDepartment(
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long id) {
        service.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
