package com.hospital.mediflow.DoctorDepartments.Services.Concretes;

import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Services.Abstracts.DoctorDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorDepartmentServiceImpl implements DoctorDepartmentService {
    private final DoctorDepartmentDataService dataService;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('manager:read')")
    public List<DoctorDepartmentResponseDto> findAll(DoctorDepartmentFilterDto filterDto) {
        return dataService.findAll(filterDto);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:read')")
    public Page<DoctorDepartmentResponseDto> findAll(Pageable pageable, DoctorDepartmentFilterDto filterDto) {
        return dataService.findAll(pageable,filterDto);
    }


    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:create')")
    public DoctorDepartmentResponseDto signDoctorsToDepartment(List<Long> doctorIds, Long departmentId) {
        dataService.assignDoctorsToDepartment(doctorIds,departmentId);
        return dataService.findByDepartmentId(departmentId);

    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:delete')")
    public DoctorDepartmentResponseDto removeDoctorFromDepartment(List<Long> doctorIds, Long departmentId) {
        for(Long doctorId :doctorIds){
            dataService.removeDoctorFromDepartment(doctorId,departmentId);

        }
        return dataService.findByDepartmentId(departmentId);
    }
}
