package com.hospital.mediflow.MedicalRecords.Services.Concretes;

import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordDataService dataService;

    @Override
    public List<MedicalRecordResponseDto> findAllMedicalRecords(MedicalRecordFilterDto medicalRecordFilter) {
        return dataService.findAllMedicalRecords(medicalRecordFilter);
    }

    @Override
    public Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, MedicalRecordFilterDto medicalRecordFilter) {
        return dataService.findAllMedicalRecords(pageable,medicalRecordFilter);
    }

    @Override
    public MedicalRecordResponseDto findMedicalRecordById(Long id) {
        return dataService.findMedicalRecordById(id);
    }

    @Override
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto medicalRecord) {
        return dataService.createMedicalRecord(medicalRecord);
    }

    @Override
    public MedicalRecordResponseDto updateMedicalRecord(Long id, MedicalRecordRequestDto medicalRecord) {
        return dataService.updateMedicalRecord(id, medicalRecord);
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        dataService.deleteMedicalRecord(id);
    }
}
