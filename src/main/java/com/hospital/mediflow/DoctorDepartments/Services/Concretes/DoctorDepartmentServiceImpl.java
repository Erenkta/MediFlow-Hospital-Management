package com.hospital.mediflow.DoctorDepartments.Services.Concretes;

import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentResponseDto;
import com.hospital.mediflow.DoctorDepartments.Services.Abstracts.DoctorDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorDepartmentServiceImpl implements DoctorDepartmentService {
    private final DoctorDepartmentDataService dataService;

    @Override
    public List<DoctorDepartmentResponseDto> findAll() {
        return dataService.findAll();
    }

    @Override
    public Page<DoctorDepartmentResponseDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public DoctorDepartmentResponseDto signDoctorsToDepartment(List<Long> doctorIds, Long departmentId) {
        dataService.assignDoctorsToDepartment(doctorIds,departmentId);
        return dataService.findByDepartmentId(departmentId);

    }

    @Override
    public DoctorDepartmentResponseDto removeDoctorFromDepartment(List<Long> doctorIds, Long departmentId) {
        for(Long doctorId :doctorIds){
            dataService.removeDoctorFromDepartment(doctorId,departmentId);

        }
        return dataService.findByDepartmentId(departmentId);
    }
}
