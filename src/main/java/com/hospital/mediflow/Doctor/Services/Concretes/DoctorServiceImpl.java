package com.hospital.mediflow.Doctor.Services.Concretes;

import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorDataService dataService;
    @Override
    public DoctorResponseDto saveDoctor(DoctorRequestDto requestDto) {
        return dataService.save(requestDto);
    }

    @Override
    public Optional<DoctorResponseDto> findDoctorById(Long id) {
        return dataService.findById(id);
    }

    @Override
    public Optional<DoctorResponseDto> findDoctorByDoctorCode(Long doctorCode) {
        return Optional.empty();
    }
}
