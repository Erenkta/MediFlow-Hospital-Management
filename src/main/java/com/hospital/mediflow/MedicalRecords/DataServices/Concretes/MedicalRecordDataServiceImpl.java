package com.hospital.mediflow.MedicalRecords.DataServices.Concretes;

import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Common.Helpers.Predicate.MedicalRecordPredicateBuilder;
import com.hospital.mediflow.Mappers.MedicalRecordMapper;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
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

@Service
@Slf4j
public class MedicalRecordDataServiceImpl extends BaseService<MedicalRecord,Long> implements MedicalRecordDataService {
    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;
    private final MedicalRecordPredicateBuilder filterBuilder;

    public MedicalRecordDataServiceImpl(MedicalRecordRepository repository,MedicalRecordMapper mapper,MedicalRecordPredicateBuilder filterBuilder) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
        this.filterBuilder = filterBuilder;
    }

    @Override
    public List<MedicalRecordResponseDto> findAllMedicalRecords(MedicalRecordFilterDto medicalRecordFilter) {
        Predicate filter = filterBuilder
                .withDoctorId(medicalRecordFilter.doctorId())
                .withDoctorName(medicalRecordFilter.doctorName())
                .withPatientId(medicalRecordFilter.patientId())
                .withPatientName(medicalRecordFilter.patientName())
                .withDepartmentName(medicalRecordFilter.departmentName())
                .withRecordDate(medicalRecordFilter.recordDateStart(),medicalRecordFilter.recordDateEnd())
                .build();
        return repository.findAll(filter).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, MedicalRecordFilterDto medicalRecordFilter) {
        Predicate filter = filterBuilder
                .withDoctorId(medicalRecordFilter.doctorId())
                .withDoctorName(medicalRecordFilter.doctorName())
                .withPatientId(medicalRecordFilter.patientId())
                .withPatientName(medicalRecordFilter.patientName())
                .withDepartmentName(medicalRecordFilter.departmentName())
                .withRecordDate(medicalRecordFilter.recordDateStart(),medicalRecordFilter.recordDateEnd())
                .build();
        return repository.findAll(filter,pageable).map(mapper::toDto);
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
