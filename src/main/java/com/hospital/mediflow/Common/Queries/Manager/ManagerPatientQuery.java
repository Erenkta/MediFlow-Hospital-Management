package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerPatientAccess;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
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

    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.READ_BY_ID,
            idParam = "patientId"

    )
    public PatientResponseDto findPatientById(Long patientId) {
        return patientService.findById(patientId);
    }

    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.UPDATE,
            idParam = "patientId",
            payloadParam = "requestDto"

    )
    public PatientResponseDto updatePatient(Long patientId, PatientRequestDto requestDto) {
        return patientService.update(patientId,new PatientRequestDto(requestDto.phone(),requestDto.email()));
    }

    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.DELETE,
            idParam = "patientId"
    )
    public void deletePatient(Long patientId) {
        patientService.deleteById(patientId);
    }

    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.CREATE,
            payloadParam = "requestDto"
    )
    public PatientResponseDto save(PatientRequestDto requestDto) {
        return patientService.save(requestDto);
    }
}
