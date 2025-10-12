package com.hospital.mediflow.DoctorDepartments.DataServices.Concretes;

import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.hospital.mediflow.Department.DataServices.Abstracts.DepartmentDataService;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Department.Repository.DepartmentRepository;
import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Repositories.DoctorRepository;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentId;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.DoctorDepartments.Repositories.DoctorDepartmentRepository;
import com.hospital.mediflow.Mappers.DoctorDepartmentMapper;
import com.hospital.mediflow.Mappers.DoctorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorDepartmentDataServiceImpl implements DoctorDepartmentDataService{
    private final DoctorDepartmentRepository repository;
    private final DoctorDataService doctorDataService;
    private final DepartmentDataService departmentDataService;
    private final DoctorDepartmentMapper mapper;
    private final DoctorMapper doctorMapper;
    @Override
    public List<DoctorDepartmentResponseDto> findAll() {
        return repository.findAllWithDoctors().stream()
                .map(department -> {
                    List<DoctorResponseDto> doctors = department.getDoctorDepartments().stream()
                            .map(dd -> doctorMapper.toDto(dd.getDoctor()))
                            .toList();
                    return mapper.toDto(department, doctors);
                }).toList();
    }
    @Override
    public void assignDoctorsToDepartment(List<Long> doctorIds, Long departmentId) {
        // TODO Check if a doctor already assigned to another department.If it does, then first unassign the doctor first

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
        DoctorDepartment doctorDepartment = repository.findById(new DoctorDepartmentId(doctorId,departmentId))
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
        List<DoctorResponseDto> doctors =  repository.findDepartmentDoctors(departmentId);
        return mapper.toDto(department,doctors);
    }
}
