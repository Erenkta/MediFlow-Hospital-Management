package com.hospital.mediflow.Specialty.Services.Abstracts;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface SpecialtyService {
    SpecialtyResponseDto createSpecialty(SpecialtyRequestDto requestDto);
    SpecialtyResponseDto updateSpecialty(@NotBlank String code, SpecialtyRequestDto requestDto);
    SpecialtyResponseDto findSpecialtyByCode(@NotBlank String code);
    List<SpecialtyResponseDto> findAllSpecialties();
    void deleteSpecialty(@NotBlank String code);
}
