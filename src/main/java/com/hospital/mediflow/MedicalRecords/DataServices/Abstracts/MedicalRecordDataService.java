package com.hospital.mediflow.MedicalRecords.DataServices.Abstracts;

import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.MedicalRecord;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordDataService {
    List<MedicalRecordResponseDto> findAllMedicalRecords(Predicate medicalRecordFilter);
    Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, Predicate medicalRecordFilter);
    Optional<Long> findDoctorIdByRecordId(Long recordId);
    MedicalRecord findReferenceById(Long recordId);
    boolean isPatientRecordRelationExists(Long recordId, Long patientId);
    boolean isDoctorRecordRelationExists(Long recordId, Long doctorId);
    MedicalRecordResponseDto findMedicalRecordById(Long id);
    MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto medicalRecord);
    MedicalRecordResponseDto updateMedicalRecord(Long id,MedicalRecordRequestDto medicalRecord);
    void deleteMedicalRecord(Long id);
}
