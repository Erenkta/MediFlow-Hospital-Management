package com.hospital.mediflow.Common.Queries.Doctor;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DoctorQuery {
    private final DoctorService doctorService;

    @PreAuthorize("#doctorId == authentication.principal.resourceId")
    public DoctorResponseDto updateDoctor(Long doctorId, DoctorRequestDto request) {
        DoctorRequestDto updateBody = request.DoctorRequest();
        return doctorService.updateDoctor(doctorId,updateBody);
    }
}
