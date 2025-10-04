package com.hospital.mediflow.Specialty.DataServices.Abstracts;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;

import java.util.List;

public interface SpecialtyDataService {
    SpecialtyResponseDto findSpecialtyByCode(String code);
    SpecialtyResponseDto updateSpecialty(String code,SpecialtyRequestDto requestDto);
    SpecialtyResponseDto createSpecialty(SpecialtyRequestDto requestDto);
    List<SpecialtyResponseDto> findAllSpecialties();
}
