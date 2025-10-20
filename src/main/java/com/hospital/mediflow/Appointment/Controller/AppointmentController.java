package com.hospital.mediflow.Appointment.Controller;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<? extends Iterable<AppointmentResponseDto>> findAll(Pageable pageable, AppointmentFilterDto filterDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                pageable.isUnpaged()
                        ? appointmentService.findAll(filterDto)
                        : appointmentService.findAll(pageable,filterDto)
        );
    }
    @GetMapping("/{appointment-id}")
    public ResponseEntity<AppointmentResponseDto> findById(@PathVariable("appointment-id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findById(id));
    }
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> save(@RequestBody @Valid AppointmentRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(requestDto));
    }
    @PutMapping("/{appointment-id}")
    public ResponseEntity<AppointmentResponseDto> update(@PathVariable(name = "appointment-id") Long id,@RequestBody AppointmentRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.update(id, requestDto));
    }
    @DeleteMapping("/{appointment-id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "appointment-id") Long id){
        appointmentService.deleteById(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
