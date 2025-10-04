package com.hospital.mediflow.Specialty.Services.Concretes;

import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyDataService dataService;
    @Override
    public SpecialtyResponseDto findSpecialtyByCode(String code) {
        return dataService.findSpecialtyByCode(code);
    }
}
