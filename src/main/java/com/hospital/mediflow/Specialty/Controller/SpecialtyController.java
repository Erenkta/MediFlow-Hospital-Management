package com.hospital.mediflow.Specialty.Controller;

import com.hospital.mediflow.Common.Exceptions.ErrorResponse;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/specialties")
@RequiredArgsConstructor
public class SpecialtyController {
    private final SpecialtyService service;

    @Operation(summary = "Create a new specialty", description = "Creates a new specialty in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Specialty created successfully",
                    content = @Content(schema = @Schema(implementation = SpecialtyResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<SpecialtyResponseDto> createSpecialty(
            @Parameter(description = "Specialty data to create", required = true)
            @RequestBody SpecialtyRequestDto request) {
        return ResponseEntity.status(201).body(service.createSpecialty(request));
    }

    @Operation(summary = "Get all specialties", description = "Returns a list of all specialties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of specialties retrieved",
                    content = @Content(schema = @Schema(implementation = SpecialtyResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<SpecialtyResponseDto>> getSpecialties() {
        return ResponseEntity.status(200).body(service.findAllSpecialties());
    }

    @Operation(summary = "Get specialty by code", description = "Retrieve a single specialty by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Specialty found",
                    content = @Content(schema = @Schema(implementation = SpecialtyResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Specialty not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{code}")
    public ResponseEntity<SpecialtyResponseDto> getSpecialtyByCode(
            @Parameter(description = "Code of the specialty to retrieve", required = true)
            @PathVariable(name = "code") String code) {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findSpecialtyByCode(code));
    }

    @Operation(summary = "Update a specialty", description = "Updates an existing specialty by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specialty updated successfully",
                    content = @Content(schema = @Schema(implementation = SpecialtyResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Specialty not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{code}")
    public ResponseEntity<SpecialtyResponseDto> updateSpecialty(
            @Parameter(description = "Code of the specialty to update", required = true)
            @PathVariable(name = "code") String code,
            @Parameter(description = "Updated specialty data", required = true)
            @RequestBody SpecialtyRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateSpecialty(code, requestDto));
    }

    @Operation(summary = "Delete a specialty", description = "Deletes a specialty by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Specialty deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Specialty not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteSpecialty(
            @Parameter(description = "Code of the specialty to delete", required = true)
            @PathVariable(name = "code") String code) {
        service.deleteSpecialty(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
