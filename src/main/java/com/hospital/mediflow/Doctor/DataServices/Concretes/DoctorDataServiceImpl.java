package com.hospital.mediflow.Doctor.DataServices.Concretes;


import com.hospital.mediflow.Common.BaseService;
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
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Services.Abstracts.SpecialtyService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class DoctorDataServiceImpl extends BaseService<Doctor,Long>  implements DoctorDataService {
    private final DoctorRepository repository;
    private final DoctorMapper mapper;
    private final SpecialtyService specialtyService;

    public DoctorDataServiceImpl(DoctorRepository repository, DoctorMapper mapper, SpecialtyService specialtyService) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
        this.specialtyService = specialtyService;
    }

    @Override
    public DoctorResponseDto save(DoctorRequestDto requestDto) {
        Doctor entity = mapper.toEntity(requestDto);
        SpecialtyResponseDto specialtyResponseDto =specialtyService.findSpecialtyByCode(requestDto.specialty());
        entity.getSpecialty().setName(specialtyResponseDto.name());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public DoctorResponseDto update(Long id, DoctorRequestDto requestDto) {
            Doctor entity = this.findByIdOrThrow(id);
            Doctor updatedEntity = mapper.toUpdatedEntity(entity,requestDto);

            if(Objects.nonNull(requestDto.specialty())){
                SpecialtyResponseDto specialtyResponseDto =specialtyService.findSpecialtyByCode(requestDto.specialty());
                updatedEntity.getSpecialty().setName(specialtyResponseDto.name());
            }
        return mapper.toDto(repository.save(updatedEntity));
    }

    @Override
    public DoctorResponseDto findById(Long id) {
        return  mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public List<DoctorResponseDto> findByDoctorCode(String specialty, TitleEnum title) {
        return repository.findAll(DoctorSpecification.hasDoctorCode(specialty,title)).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<DoctorResponseDto> findByDoctorCode(Pageable pageable, String specialty, TitleEnum title) {
        return repository.findAll(DoctorSpecification.hasDoctorCode(specialty,title),pageable).map(mapper::toDto);
    }

    @Override
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
        this.isExistsOrThrow(id);
        repository.deleteById(id);
    }

    @Override
    public Doctor getReferenceById(Long doctorId){
        return this.findByIdOrThrow(doctorId);
    }
}
