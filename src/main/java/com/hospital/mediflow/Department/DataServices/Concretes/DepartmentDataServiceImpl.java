package com.hospital.mediflow.Department.DataServices.Concretes;

import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.RecordAlreadyExistException;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.hospital.mediflow.Common.Specifications.DepartmentSpecification;
import com.hospital.mediflow.Department.DataServices.Abstracts.DepartmentDataService;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentResponseDto;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Department.Repository.DepartmentRepository;
import com.hospital.mediflow.Mappers.DepartmentMapper;
import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DepartmentDataServiceImpl extends BaseService<Department, Long> implements DepartmentDataService {
    private final DepartmentMapper mapper;
    private final SpecialtyDataService specialtyDataService;
    private final DepartmentRepository repository;

    public DepartmentDataServiceImpl(DepartmentRepository repository,
                                     DepartmentMapper mapper,
                                     SpecialtyDataService specialtyDataService) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
        this.specialtyDataService = specialtyDataService;
    }

    @Override
    public List<DepartmentResponseDto> findAllDepartments(DepartmentFilterDto filterDto) {
        return repository.findAll(DepartmentSpecification.filter(filterDto)).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<DepartmentResponseDto> findAllDepartments(Pageable pageable, DepartmentFilterDto filterDto) {
       return repository.findAll(DepartmentSpecification.filter(filterDto),pageable).map(mapper::toDto);
    }

    @Override
    public DepartmentResponseDto findDepartmentById(Long id) {
        return mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public Department getReferenceById(Long departmentId) {
        // TODO first check if department is exists.
        return this.findByIdOrThrow(departmentId);
    }

    @Override
    @Transactional
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {
        if(repository.existsByName(departmentRequestDto.name())){
            String message = String.format("Department with name %s already exists", departmentRequestDto.name());
            throw new RecordAlreadyExistException(message,ErrorCode.RECORD_ALREADY_EXISTS);
        }
        Department entity = repository.save(mapper.toEntity(departmentRequestDto));
        List<Specialty> specialties = specialtyDataService.assignDepartment(departmentRequestDto.specialties(),entity);
        entity.setSpecialties(specialties);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public DepartmentResponseDto updateDepartment(Long id,DepartmentRequestDto departmentRequestDto) {
        Department entity = this.findByIdOrThrow(id);
        mapper.toEntity(entity,departmentRequestDto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department entity = this.findByIdOrThrow(id);
        ArrayList<String> specialtyCodes =  new ArrayList<>(entity.getSpecialties().stream().map(Specialty::getCode).toList());
        specialtyDataService.dismissDepartment(specialtyCodes);
        repository.deleteById(id);
    }

    @Override
    public DepartmentResponseDto addSpecialties(Long id, List<String> specialties) {
        Department entity = this.findByIdOrThrow(id);
        List<Specialty> specialtyList = specialtyDataService.assignDepartment(specialties,entity);
        entity.getSpecialties().addAll(specialtyList);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public DepartmentResponseDto removeSpecialties(Long id, List<String> specialties) {
        Department entity = this.findByIdOrThrow(id);
        List<Specialty> specialtyList = specialtyDataService.dismissDepartment(specialties);
        entity.getSpecialties().removeAll(specialtyList);
        repository.save(entity);
        return mapper.toDto(entity);
    }
}
