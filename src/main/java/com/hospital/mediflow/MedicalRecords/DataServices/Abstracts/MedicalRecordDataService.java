package com.hospital.mediflow.MedicalRecords.DataServices.Abstracts;

import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MedicalRecordDataService {
    List<MedicalRecordResponseDto> findAllMedicalRecords(MedicalRecordFilterDto medicalRecordFilter);
    Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, MedicalRecordFilterDto medicalRecordFilter);
    MedicalRecordResponseDto findMedicalRecordById(Long id);
    MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto medicalRecord);
    MedicalRecordResponseDto updateMedicalRecord(Long id,MedicalRecordRequestDto medicalRecord);
    void deleteMedicalRecord(Long id);
}
