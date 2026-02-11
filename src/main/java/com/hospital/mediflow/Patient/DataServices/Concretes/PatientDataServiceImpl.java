package com.hospital.mediflow.Patient.DataServices.Concretes;

import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Mappers.PatientMapper;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import com.hospital.mediflow.Patient.Repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PatientDataServiceImpl extends BaseService<Patient,Long> implements PatientDataService {
    private final PatientMapper mapper;
    public PatientDataServiceImpl(PatientRepository repository, PatientMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    public List<PatientResponseDto> findAll(Specification<Patient> filterDto) {
        return ((PatientRepository)repository).findAll(filterDto).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<PatientResponseDto> findAll(Pageable pageable, Specification<Patient> filterDto) {
        return ((PatientRepository)repository).findAll(filterDto,pageable).map(mapper::toDto);
    }

    @Override
    public PatientResponseDto findById(Long id) {
        return mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public boolean isDoctorPatientRelationExists(Long doctorId, Long patientId) {
        return ((PatientRepository)repository).isDoctorPatientRelationExists(doctorId,patientId);
    }
    @Override
    public boolean isDepartmentPatientRelationExists(Long departmentId, Long patientId) {
        return ((PatientRepository)repository).isDepartmentPatientRelationExists(departmentId,patientId);
    }

    @Override
    public Patient getReferenceById(Long id) {
        return this.findByIdOrThrow(id);
    }

    @Override
    public PatientResponseDto save(PatientRequestDto requestDto) {
        return mapper.toDto(repository.save(mapper.toEntity(requestDto)));
    }

    @Override
    public PatientResponseDto update(Long id, PatientRequestDto requestDto) {
        Patient entity = this.findByIdOrThrow(id);
        mapper.updateEntity(entity,requestDto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        isExistsOrThrow(id);
        repository.deleteById(id);
    }
}
