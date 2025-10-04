package com.hospital.mediflow.Specialty.Services.Abstracts;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;

public interface SpecialtyService {
    SpecialtyResponseDto findSpecialtyByCode(String code);
}
