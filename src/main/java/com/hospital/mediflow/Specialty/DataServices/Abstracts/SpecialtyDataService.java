package com.hospital.mediflow.Specialty.DataServices.Abstracts;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyDto;

public interface SpecialtyDataService {
    public SpecialtyDto findSpecialtyByCode(String code);
}
