package com.hospital.mediflow.MedicalRecords.DataServices.Concretes;

import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Mappers.MedicalRecordMapper;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.MedicalRecord;

import com.hospital.mediflow.MedicalRecords.Repository.MedicalRecordRepository;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MedicalRecordDataServiceImpl extends BaseService<MedicalRecord,Long> implements MedicalRecordDataService {
    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;

    public MedicalRecordDataServiceImpl(MedicalRecordRepository repository,MedicalRecordMapper mapper) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;

    }

    @Override
    public List<MedicalRecordResponseDto> findAllMedicalRecords(Predicate medicalRecordFilter) {

        return repository.findAll(medicalRecordFilter).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, Predicate medicalRecordFilter) {
        return repository.findAll(medicalRecordFilter,pageable).map(mapper::toDto);
    }

    @Override
    public Optional<Long> findDoctorIdByRecordId(Long recordId) {
        return Optional.of(repository.findDoctorIdByRecordId(recordId));
    }

    @Override
    public boolean isPatientRecordRelationExists(Long recordId, Long patientId) {
        return repository.isPatientRecordRelationExists(recordId,patientId);
    }

    @Override
    public boolean isDoctorRecordRelationExists(Long recordId, Long doctorId) {
        return repository.isDoctorRecordRelationExists(recordId,doctorId);
    }
    @Override
    public boolean isManagerRecordRelationExists(Long recordId, Long departmentId) {
        return repository.isManagerRecordRelationExists(recordId,departmentId);
    }

    @Override
    public MedicalRecordResponseDto findMedicalRecordById(Long id) {
        return  mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto medicalRecord) {
        MedicalRecord entity = mapper.toEntity(medicalRecord);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public MedicalRecordResponseDto updateMedicalRecord(Long id, MedicalRecordRequestDto medicalRecord) {
        MedicalRecord entity = this.findByIdOrThrow(id);
        mapper.toEntity(entity,medicalRecord);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        this.isExistsOrThrow(id);
        repository.deleteById(id);
    }
}
