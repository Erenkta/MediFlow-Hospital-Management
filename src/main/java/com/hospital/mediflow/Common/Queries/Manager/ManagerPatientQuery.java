package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.Common.Annotations.ManagerPatientAccess;
import com.hospital.mediflow.Common.Specifications.PatientSpecification;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerPatientQuery {
    private final PatientDataService patientDataService;

    public List<PatientResponseDto> findPatient(PatientFilterDto filter) {
        Long departmentId = MediflowUserDetailsService.currentUser().getResourceId();
        Specification<Patient> spec = PatientSpecification.hasFilter(filter);
        spec = spec.and(PatientSpecification.withDepartmentId(departmentId));
        return patientDataService.findAll(spec);
    }
    public Page<PatientResponseDto> findPatient(Pageable pageable, PatientFilterDto filter) {
        Long departmentId = MediflowUserDetailsService.currentUser().getResourceId();
        Specification<Patient> spec = PatientSpecification.hasFilter(filter);
        spec = spec.and(PatientSpecification.withDepartmentId(departmentId));
        return patientDataService.findAll(pageable,spec);
    }
    @ManagerPatientAccess
    public PatientResponseDto findPatientById(Long patientId) {
        return patientDataService.findById(patientId);
    }

    @ManagerPatientAccess
    public PatientResponseDto updatePatient(Long patientId, PatientRequestDto requestDto) {
        return patientDataService.update(patientId,new PatientRequestDto(requestDto.phone(),requestDto.email()));
    }

    @ManagerPatientAccess
    public void deletePatient(Long patientId) {
        patientDataService.deleteById(patientId);
    }
}
