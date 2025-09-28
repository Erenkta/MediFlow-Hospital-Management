package com.hospital.mediflow.Doctor.DataServices.Abstracts;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface DoctorDataService {
    DoctorResponseDto save(DoctorRequestDto requestDto);
    Optional<DoctorResponseDto> findById(Long id);
    Optional<DoctorResponseDto> findByDoctorCode(Long doctorCode);
    List<DoctorResponseDto> findAll();
    Page<DoctorResponseDto> findAll(Pageable pageable, DoctorFilterDto filter);
    List<DoctorResponseDto> findAll(DoctorFilterDto filter);
}
