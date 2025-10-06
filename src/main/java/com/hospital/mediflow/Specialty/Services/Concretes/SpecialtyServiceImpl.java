package com.hospital.mediflow.Specialty.Services.Concretes;

import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyDataService dataService;

    @Override
    public SpecialtyResponseDto createSpecialty(SpecialtyRequestDto requestDto) {
        return dataService.createSpecialty(requestDto);
    }

    @Override
    public SpecialtyResponseDto updateSpecialty(@NotBlank String code, SpecialtyRequestDto requestDto) {
        return dataService.updateSpecialty(code,requestDto);
    }

    @Override
    public SpecialtyResponseDto findSpecialtyByCode(@NotBlank String code) {
        return dataService.findSpecialtyByCode(code);
    }

    @Override
    public List<SpecialtyResponseDto> findAllSpecialties() {
        return dataService.findAllSpecialties();
    }

    @Override
    public void deleteSpecialty(String code) {
        dataService.deleteSpecialty(code);
    }
}
