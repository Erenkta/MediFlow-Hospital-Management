package com.hospital.mediflow.MedicalRecords.Services.Abstracts;


import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated // This is used in here because JDK Dynamic Proxy only consider interface annotations
public interface MedicalRecordService {
    List<MedicalRecordResponseDto> findAllMedicalRecords(Predicate medicalRecordFilter);
    Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, Predicate medicalRecordFilter);
    MedicalRecordResponseDto findMedicalRecordById(@NotNull Long id);
    MedicalRecordResponseDto createMedicalRecord(@Valid MedicalRecordRequestDto medicalRecord);
    MedicalRecordResponseDto updateMedicalRecord(@NotNull Long id, MedicalRecordRequestDto medicalRecord);
    void deleteMedicalRecord(@NotNull Long id);
}
