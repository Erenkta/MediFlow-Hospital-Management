package com.hospital.mediflow.Patient.Services.Abstracts;

import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    List<PatientResponseDto> findAll(PatientFilterDto filterDto);
    Page<PatientResponseDto> findAll(Pageable pageable, PatientFilterDto filterDto);
    PatientResponseDto findById(@NotNull Long id);
    PatientResponseDto save(PatientRequestDto requestDto);
    PatientResponseDto update(@NotNull Long id,PatientRequestDto requestDto);
    void deleteById(@NotNull Long id);
}
