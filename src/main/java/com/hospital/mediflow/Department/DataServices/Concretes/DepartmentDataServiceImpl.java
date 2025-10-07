package com.hospital.mediflow.Department.DataServices.Concretes;

import com.hospital.mediflow.Common.Exceptions.ErrorCode;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentDataServiceImpl implements DepartmentDataService {
    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final SpecialtyDataService specialtyDataService;
    @Override
    public List<DepartmentResponseDto> findAllDepartments(DepartmentFilterDto filterDto) {
        return repository.findAll(DepartmentSpecification.hasName(filterDto.name()).and(DepartmentSpecification.hasDescription(filterDto.description()))).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<DepartmentResponseDto> findAllDepartments(Pageable pageable, DepartmentFilterDto filterDto) {
       return repository.findAll(DepartmentSpecification.filter(filterDto),pageable).map(mapper::toDto);
    }

    @Override
    public DepartmentResponseDto findDepartmentById(Long id) {
       Department department = findDepartmentEntityById(id);
        return mapper.toDto(department);
    }

    private Department findDepartmentEntityById(Long id){
        // Should I implement the AOP to handle throwing same kind of exceptions from different services ?
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Department with id %s couldn't be found. Please try again with different ID", id),
                        ErrorCode.RECORD_NOT_FOUND
                ));
    }

    @Override
    @Transactional
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {
        Department entity = repository.save(mapper.toEntity(departmentRequestDto));
        specialtyDataService.createBulkSpecialty(departmentRequestDto.specialties(),entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public DepartmentResponseDto updateDepartment(Long id,DepartmentRequestDto departmentRequestDto) {
        Department entity = findDepartmentEntityById(id);
        mapper.toEntity(entity,departmentRequestDto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public void deleteDepartment(Long id) {
        boolean isRepositoryExists = repository.existsById(id);
        if(isRepositoryExists) repository.deleteById(id );
    }
}
