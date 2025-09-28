package com.hospital.mediflow.Doctor.Services.Abstracts;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;

import java.util.Optional;

public interface DoctorService {
    DoctorResponseDto saveDoctor(DoctorRequestDto requestDto);
    Optional<DoctorResponseDto> findDoctorById(Long id);
    Optional<DoctorResponseDto> findDoctorByDoctorCode(Long doctorCode);
}
