package com.hospital.mediflow.Specialty.Services.Abstracts;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;

import java.util.List;

public interface SpecialtyService {
    SpecialtyResponseDto createSpecialty(SpecialtyRequestDto requestDto);
    SpecialtyResponseDto updateSpecialty(String code, SpecialtyRequestDto requestDto);
    SpecialtyResponseDto findSpecialtyByCode(String code);
    List<SpecialtyResponseDto> findAllSpecialties();
}
