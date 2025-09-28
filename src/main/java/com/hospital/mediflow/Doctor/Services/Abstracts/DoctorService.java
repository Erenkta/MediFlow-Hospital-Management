package com.hospital.mediflow.Doctor.Services.Abstracts;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    DoctorResponseDto saveDoctor(DoctorRequestDto requestDto);
    DoctorResponseDto updateDoctor(Long id,DoctorRequestDto requestDto);
    DoctorResponseDto findDoctorById(Long id);
    DoctorResponseDto findDoctorByDoctorCode(Long doctorCode);
    List<DoctorResponseDto> findDoctors(DoctorFilterDto filter);
    Page<DoctorResponseDto> findDoctors(Pageable pageable, DoctorFilterDto filter);

}
