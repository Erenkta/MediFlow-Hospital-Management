package com.hospital.mediflow.Specialty.Services.Concretes;

import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyDto;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyDataService dataService;
    @Override
    public SpecialtyDto findSpecialtyByCode(String code) {
        return dataService.findSpecialtyByCode(code);
    }
}
