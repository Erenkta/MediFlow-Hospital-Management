package com.hospital.mediflow.DoctorDepartments.DataServices.Concretes;

import com.hospital.mediflow.Common.Exceptions.DoctorIsNotSuitableForDepartment;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.hospital.mediflow.Common.Specifications.DoctorDepartmentSpecification;
import com.hospital.mediflow.Department.DataServices.Abstracts.DepartmentDataService;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Department.Repository.DepartmentRepository;
import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Repositories.DoctorRepository;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentId;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.IncompatibleDoctorProjection;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.DoctorDepartments.Repositories.DoctorDepartmentRepository;
import com.hospital.mediflow.Mappers.DoctorDepartmentMapper;
import com.hospital.mediflow.Mappers.DoctorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.hospital.mediflow.Common.Exceptions.ErrorCode.DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorDepartmentDataServiceImpl implements DoctorDepartmentDataService {
    private final DoctorDepartmentRepository repository;
    private final DoctorDataService doctorDataService;
    private final DepartmentDataService departmentDataService;
    private final DoctorDepartmentMapper mapper;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorDepartmentResponseDto> findAll(DoctorDepartmentFilterDto filterDto) {
        Set<Department> departments = new HashSet<Department>();
        repository.findAll(DoctorDepartmentSpecification.hasFilter(filterDto))
                .forEach(doctorDepartment -> {
                    Department department = doctorDepartment.getDepartment();
                     if(department.getDoctorDepartments().size() >= filterDto.departmentSize()){
                        departments.add(department);
                    }
                });
        return departments.stream().map(department -> {
            List<DoctorResponseDto> doctors = department.getDoctorDepartments().stream()
                    .map(dd -> doctorMapper.toDto(dd.getDoctor()))
                    .toList();
            return mapper.toDto(department, doctors);
        }).toList();
    }

    @Override
    public Page<DoctorDepartmentResponseDto> findAll(Pageable pageable, DoctorDepartmentFilterDto filterDto) {
        List<DoctorDepartmentResponseDto> content = this.findAll(filterDto);
        return new PageImpl<>(content,pageable,content.size());
    }

    @Override
    public void assignDoctorsToDepartment(List<Long> doctorIds, Long departmentId) {
        checkIncompatibleDoctors(doctorIds, departmentId);
        checkAlreadyAssignedDoctors(doctorIds);

        Department department = departmentDataService.getReferenceById(departmentId);
        List<DoctorDepartment> relations = doctorIds.stream()
                .map(doctorId -> DoctorDepartment.builder()
                        .id(new DoctorDepartmentId(doctorId, departmentId))
                        .doctor(doctorDataService.getReferenceById(doctorId))
                        .department(department)
                        .build())
                .toList();
        repository.saveAll(relations);
    }

    @Override
    @Transactional
    public void removeDoctorFromDepartment(Long doctorId, Long departmentId) {
        DoctorDepartment doctorDepartment = repository.findById(new DoctorDepartmentId(doctorId, departmentId))
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("No relation has found with doctor id: %s and department id :%s", doctorId, departmentId),
                        ErrorCode.RECORD_NOT_FOUND
                ));
        repository.delete(doctorDepartment);
    }

    @Override
    @Transactional
    public DoctorDepartmentResponseDto findByDepartmentId(Long departmentId) {
        Department department = departmentDataService.getReferenceById(departmentId);
        List<DoctorResponseDto> doctors = repository.findDepartmentDoctors(departmentId);
        return mapper.toDto(department, doctors);
    }

    private void checkIncompatibleDoctors(List<Long> doctorIds,Long departmentId){
        List<IncompatibleDoctorProjection> incompatibleDoctors = repository.isDoctorSpecialtyCompatible(doctorIds,departmentId);
        if(!incompatibleDoctors.isEmpty()){
            String message = String.format("Given doctor's specialties are not compatible with department's specialties (by department id %s). Please Check the doctors and try again %s",departmentId,incompatibleDoctors);
            throw new DoctorIsNotSuitableForDepartment(message, DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT);
        }
    }
    private void checkAlreadyAssignedDoctors(List<Long> doctorIds){
        List<Long> alreadyAssignedDoctors = repository.findAllAssignedDoctors(doctorIds);
        if(!alreadyAssignedDoctors.isEmpty()){
            log.info("Deleting existing assignments for doctorIds: {}", alreadyAssignedDoctors);
            repository.deleteAllByDoctorId(alreadyAssignedDoctors);
        }

    }
}
