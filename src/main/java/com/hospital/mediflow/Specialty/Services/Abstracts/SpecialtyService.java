package com.hospital.mediflow.Specialty.Services.Abstracts;

import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyDto;

import java.util.Optional;

public interface SpecialtyService {
    SpecialtyDto findSpecialtyByCode(String code);
}
