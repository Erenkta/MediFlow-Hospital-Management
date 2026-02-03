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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('doctor:read')")
    public List<PatientResponseDto> findAll(PatientFilterDto filterDto) {
        return dataService.findAll(filterDto);
    }

    @Override
    @PreAuthorize("hasAuthority('doctor:read')")
    public Page<PatientResponseDto> findAll(Pageable pageable, PatientFilterDto filterDto) {
        return dataService.findAll(pageable,filterDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('doctor:read','patient:read')")
    public PatientResponseDto findById(@NotNull Long id) {
        return dataService.findById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:create')")
    public PatientResponseDto save(PatientRequestDto requestDto) {
        return dataService.save(requestDto);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:update')")
    public PatientResponseDto update(@NotNull Long id, PatientRequestDto requestDto) {
        return dataService.update(id,requestDto);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:delete')")
    public void deleteById(@NotNull Long id) {
        dataService.deleteById(id);
    }
}
