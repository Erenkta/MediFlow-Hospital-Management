package com.hospital.mediflow.Patient.Services.Abstracts;


import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PatientService {
    List<PatientResponseDto> findAll(Specification<Patient> filterDto);
    Page<PatientResponseDto> findAll(Pageable pageable, Specification<Patient> filterDto);
    PatientResponseDto findById(@NotNull Long id);
    PatientResponseDto save(PatientRequestDto requestDto);
    PatientResponseDto update(@NotNull Long id,PatientRequestDto requestDto);
    void deleteById(@NotNull Long id);
}
