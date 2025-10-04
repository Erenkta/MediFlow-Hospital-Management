package com.hospital.mediflow.Specialty.DataServices.Abstracts;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;

public interface SpecialtyDataService {
    public SpecialtyResponseDto findSpecialtyByCode(String code);
}
