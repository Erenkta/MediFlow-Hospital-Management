package com.hospital.mediflow.Patient.Services.Concretes;

import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Services.Abstracts.PatientService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Validated
public class PatientServiceImpl implements PatientService {
    private final PatientDataService dataService;

    @Override
    public List<PatientResponseDto> findAll(PatientFilterDto filterDto) {
        return dataService.findAll(filterDto);
    }

    @Override
    public Page<PatientResponseDto> findAll(Pageable pageable, PatientFilterDto filterDto) {
        return dataService.findAll(pageable,filterDto);
    }

    @Override
    public PatientResponseDto findById(@NotNull Long id) {
        return dataService.findById(id);
    }

    @Override
    public PatientResponseDto save(PatientRequestDto requestDto) {
        return dataService.save(requestDto);
    }

    @Override
    public PatientResponseDto update(@NotNull Long id, PatientRequestDto requestDto) {
        return dataService.update(id,requestDto);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        dataService.deleteById(id);
    }
}
