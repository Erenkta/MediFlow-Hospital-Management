package com.hospital.mediflow.Patient.DataServices.Abstracts;

import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientDataService {
    List<PatientResponseDto> findAll(PatientFilterDto filterDto);
    Page<PatientResponseDto> findAll(Pageable pageable, PatientFilterDto filterDto);
    PatientResponseDto findById(Long id);
    PatientResponseDto save(PatientRequestDto patientResponseDto);
    PatientResponseDto update(Long id,PatientRequestDto patientResponseDto);
    void deleteById(Long id);
}
