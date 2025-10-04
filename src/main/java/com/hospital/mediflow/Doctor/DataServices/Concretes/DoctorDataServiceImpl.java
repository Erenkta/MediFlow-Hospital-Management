package com.hospital.mediflow.Doctor.DataServices.Concretes;


import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.hospital.mediflow.Common.Helpers.Predicate.DoctorPredicateBuilder;
import com.hospital.mediflow.Common.Specifications.DoctorSpecification;
import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Repositories.DoctorRepository;
import com.hospital.mediflow.Mappers.DoctorMapper;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyDto;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DoctorDataServiceImpl implements DoctorDataService {
    private final DoctorRepository repository;
    private final DoctorMapper mapper;
    private final SpecialtyService specialtyService;

    @Override
    @Transactional
    public DoctorResponseDto save(DoctorRequestDto requestDto) {
        Doctor entity = mapper.toEntity(requestDto);
        SpecialtyDto specialtyDto =specialtyService.findSpecialtyByCode(requestDto.specialty());
        entity.getSpecialty().setName(specialtyDto.name());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public DoctorResponseDto update(Long id, DoctorRequestDto requestDto) {
            Doctor entity = repository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException(
                            String.format("Doctor with id %s couldn't be found. Please try again with different ID", id),
                            ErrorCode.RECORD_NOT_FOUND
                    ));
            Doctor updatedEntity = mapper.toUpdatedEntity(entity,requestDto);

            SpecialtyDto specialtyDto =specialtyService.findSpecialtyByCode(requestDto.specialty());
            updatedEntity.getSpecialty().setName(specialtyDto.name());
            updatedEntity.getSpecialty().setName(specialtyDto.name());
        return mapper.toDto(repository.save(updatedEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorResponseDto> findById(Long id) {
        Optional<Doctor> entity = repository.findById(id);
        if (entity.isPresent()) {
            return mapper.toDtoOptional(entity.get());
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findByDoctorCode(String specialty, TitleEnum title) {
        return repository.findAll(DoctorSpecification.hasDoctorCode(specialty,title)).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<DoctorResponseDto> findByDoctorCode(Pageable pageable, String specialty, TitleEnum title) {
        return repository.findAll(DoctorSpecification.hasDoctorCode(specialty,title),pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponseDto> findAll(Pageable pageable, DoctorFilterDto filter) {
        DoctorPredicateBuilder filterBuilder = new DoctorPredicateBuilder();
        Predicate predicate = filterBuilder
                .withId(filter.id())
                .withFirstName(filter.firstName())
                .withLastName(filter.lastName())
                .withSpecialty(filter.specialties())
                .withTitle(filter.title())
                .build();
        return repository.findAll(predicate, pageable)
                .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findAll(DoctorFilterDto filter) {
        DoctorPredicateBuilder filterBuilder = new DoctorPredicateBuilder();
        Predicate predicate = filterBuilder
                .withId(filter.id())
                .withFirstName(filter.firstName())
                .withLastName(filter.lastName())
                .withSpecialty(filter.specialties())
                .withTitle(filter.title())
                .build();
        return repository.findAll(predicate).stream().map(mapper::toDto).toList();
    }

    @Override
    public void deleteDoctor(Long id) {
    repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Doctor with id %s couldn't be found. Please try again with different ID", id),
                        ErrorCode.RECORD_NOT_FOUND
                ));
        repository.deleteById(id);
    }
}
