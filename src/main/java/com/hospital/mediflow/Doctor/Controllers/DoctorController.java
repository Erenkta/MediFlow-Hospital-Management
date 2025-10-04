package com.hospital.mediflow.Doctor.Controllers;

import com.hospital.mediflow.Common.Exceptions.ErrorResponse;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Doctor Controller",description = "APIs for managing doctors")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService service;

    @Operation(summary = "Create a doctor", description = "Creates a new doctor with the provided information.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Doctor created successfully",
                    content = @Content(schema = @Schema(implementation = DoctorResponseDto.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)), description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody DoctorRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveDoctor(request));
    }

    @Operation(summary = "Get doctors", description = "Returns a list of doctors with optional filtering and pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)), description = "Invalid pagination or filter parameters")
    })
    @GetMapping
    public ResponseEntity<?> getDoctors(@NotNull Pageable pageable, DoctorFilterDto filter){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(service.findDoctors(filter))
                : ResponseEntity.status(HttpStatus.OK).body(service.findDoctors(pageable,filter));
    }

    @Operation(summary = "Search doctors by code",
            description = """
                    Returns doctors filtered by specialty and title, with optional pagination.
                    If both title and specialty parameters are given then filter will apply to doctor code.
                    Service will create the doctor code with title and specialty.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)),description = "Invalid parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<?> getDoctorsByDoctorCode(@NotNull Pageable pageable, @RequestParam(value = "specialty",required = false) @Valid SpecialtyEnum specialty, @Valid @RequestParam(value = "title",required = false) TitleEnum title){
        return pageable.isUnpaged()
                ? ResponseEntity.status(HttpStatus.OK).body(service.findDoctorsByDoctorCode(specialty,title))
                : ResponseEntity.status(HttpStatus.OK).body(service.findDoctorsByDoctorCode(pageable,specialty,title));
    }

    @Operation(summary = "Get doctor by ID", description = "Fetches a doctor by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = @Content(schema = @Schema(implementation = DoctorResponseDto.class))),
            @ApiResponse(responseCode = "404",content = @Content(schema = @Schema(implementation = ErrorResponse.class)), description = "Doctor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findDoctorById(id));
    }

    @Operation(summary = "Update a doctor", description = "Updates doctor details by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully",
                    content = @Content(schema = @Schema(implementation = DoctorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)), description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable("id") Long id,@RequestBody DoctorRequestDto request){
       return ResponseEntity.status(HttpStatus.OK).body(service.updateDoctor(id,request));
    }

    @Operation(summary = "Delete a doctor", description = "Deletes a doctor record by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)), description = "Doctor not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") Long id){
        service.deleteDoctor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
