package com.hospital.mediflow.Specialty.DataServices.Concretes;

import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.SpecialtyNotFoundException;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Mappers.SpecialtyMapper;
import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import com.hospital.mediflow.Specialty.Repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpecialtyDataServiceImpl extends BaseService<Specialty,String>  implements SpecialtyDataService {
    private final SpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    public SpecialtyDataServiceImpl(SpecialtyRepository repository, SpecialtyMapper mapper) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SpecialtyResponseDto findSpecialtyByCode(String code){
        return mapper.toDto(this.findByIdOrThrow(code));
    }

    @Override
    public SpecialtyResponseDto updateSpecialty(String code, SpecialtyRequestDto requestDto) {
        Specialty specialty = this.findByIdOrThrow(code);
        mapper.toEntity(specialty,requestDto);
        repository.save(specialty);
        return mapper.toDto(specialty);
    }

    @Override
    public SpecialtyResponseDto createSpecialty(SpecialtyRequestDto requestDto) {
        Specialty specialty = mapper.toEntity(requestDto);
        Integer specialtyCount = repository.countSpecialties();
        specialty.createCode(specialtyCount);
        return mapper.toDto(repository.save(specialty));
    }

    @Override
    public List<Specialty> assignDepartment(List<String> specialtyIds, Department department) {
        List<Specialty> specialties = repository.findAllById(specialtyIds);
        specialties.forEach(item -> {
            specialtyIds.remove(item.getCode());
            item.setDepartment(department);
        });
        handleInvalidSpecialties(specialtyIds,specialties);
        return specialties;
    }

    @Override
    public List<Specialty> dismissDepartment(List<String> specialtyIds) {
        List<Specialty> specialties = repository.findAllById(specialtyIds);
        specialties.forEach(item -> {
            specialtyIds.remove(item.getCode());
            item.setDepartment(null);
        });
        handleInvalidSpecialties(specialtyIds,specialties);
        return specialties;
    }

    @Override
    public List<SpecialtyResponseDto> findAllSpecialties() {
        return repository.findAllByOrderByCodeAsc().stream().map(mapper::toDto).toList();
    }

    @Override
    public void deleteSpecialty(String code) {
        this.isExistsOrThrow(code);
        repository.deleteById(code);
    }

    private void handleInvalidSpecialties(List<String> specialtyIds,List<Specialty> specialties){
        if(!specialtyIds.isEmpty()){
            String message = String.format(
                    "Some of the given specialties could not be found. Please check the specialty codes and try again. Check List: %s",
                    specialtyIds
            );            throw new SpecialtyNotFoundException(message,ErrorCode.RECORD_NOT_FOUND);
        }
        repository.saveAll(specialties);
    }
}
