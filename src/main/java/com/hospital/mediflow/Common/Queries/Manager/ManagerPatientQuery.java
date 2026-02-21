package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerPatientAccess;
import com.hospital.mediflow.Common.Annotations.AccessType;
import com.hospital.mediflow.Common.Specifications.PatientSpecification;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import com.hospital.mediflow.Patient.Services.Abstracts.PatientService;
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
    private final PatientService patientService;

    public List<PatientResponseDto> findPatient(PatientFilterDto filter) {
        Long departmentId = MediflowUserDetailsService.currentUser().getResourceId();
        Specification<Patient> spec = PatientSpecification.hasFilter(filter);
        spec = spec.and(PatientSpecification.withDepartmentId(departmentId));
        return patientService.findAll(spec);
    }
    public Page<PatientResponseDto> findPatient(Pageable pageable, PatientFilterDto filter) {
        Long departmentId = MediflowUserDetailsService.currentUser().getResourceId();
        Specification<Patient> spec = PatientSpecification.hasFilter(filter);
        spec = spec.and(PatientSpecification.withDepartmentId(departmentId));
        return patientService.findAll(pageable,spec);
    }
    @ManagerPatientAccess(type = AccessType.READ_BY_ID)
    public PatientResponseDto findPatientById(Long patientId) {
        return patientService.findById(patientId);
    }

    @ManagerPatientAccess(type = AccessType.UPDATE)
    public PatientResponseDto updatePatient(Long patientId, PatientRequestDto requestDto) {
        return patientService.update(patientId,new PatientRequestDto(requestDto.phone(),requestDto.email()));
    }

    @ManagerPatientAccess(type = AccessType.DELETE)
    public void deletePatient(Long patientId) {
        patientService.deleteById(patientId);
    }

    @ManagerPatientAccess(type = AccessType.CREATE)
    public PatientResponseDto save(PatientRequestDto requestDto) {
        return patientService.save(requestDto);
    }
}
