package com.hospital.mediflow.DoctorDepartments.Controller;

import com.hospital.mediflow.Common.Exceptions.ErrorResponse;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Services.DoctorDepartmentQueryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/doctor-department")
@RequiredArgsConstructor
public class DoctorDepartmentController {

    private final DoctorDepartmentQueryFacade facade;

    @Operation(summary = "Get doctor-department assignments", description = "Returns all doctor-department assignments, pageable or unpaged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignments retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DoctorDepartmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAll(
            @Parameter(description = "Pageable info for pagination", required = true)
            @NotNull Pageable pageable,
            @Parameter(description = "Filter criteria for doctor-department assignments")
            DoctorDepartmentFilterDto filterDto) {

        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(facade.findAll(filterDto))
                : ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable, filterDto));
    }

    @Operation(summary = "Assign doctors to a department", description = "Adds doctors to the specified department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors assigned successfully",
                    content = @Content(schema = @Schema(implementation = DoctorDepartmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Department or doctors not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{department-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DoctorDepartmentResponseDto> save(
            @Parameter(description = "ID of the department", required = true)
            @PathVariable(name = "department-id") Long id,
            @Parameter(description = "Set of doctor IDs to assign", required = true)
            @RequestBody Set<Long> doctorIds) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.signDoctorsToDepartment(id,doctorIds));
    }

    @Operation(summary = "Remove doctors from a department", description = "Removes doctors from the specified department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors removed successfully",
                    content = @Content(schema = @Schema(implementation = DoctorDepartmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Department or doctors not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{department-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DoctorDepartmentResponseDto> delete(
            @Parameter(description = "ID of the department", required = true)
            @PathVariable(name = "department-id") Long id,
            @Parameter(description = "List of doctor IDs to remove", required = true)
            @RequestBody Set<Long> doctorIds) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.removeDoctorFromDepartment(id,doctorIds));
    }
}