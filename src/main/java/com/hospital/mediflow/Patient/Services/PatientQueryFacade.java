package com.hospital.mediflow.Patient.Services;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.FilterManager;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Common.Specifications.PatientSpecification;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Services.Abstracts.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PatientQueryFacade {

    private final PatientService patientService;


    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.READ_BY_FILTER,
            filterParam = "filter"
    )
    @FilterManager(
            filterClass = PatientFilterDto.class,
            resourceType = ResourceType.PATIENT,
            filterParam = "filter"
    )
    public List<PatientResponseDto> findPatient(PatientFilterDto filter) {
        return patientService.findAll(PatientSpecification.hasFilter(filter));
    }

    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.READ_BY_FILTER,
            filterParam = "filter"
    )
    @FilterManager(
            filterClass = PatientFilterDto.class,
            resourceType = ResourceType.PATIENT,
            filterParam = "filter"
    )
    public Page<PatientResponseDto> findPatient(Pageable pageable, PatientFilterDto filter) {
        return patientService.findAll(pageable,PatientSpecification.hasFilter(filter));
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
            action = AccessType.CREATE,
            resource = ResourceType.PATIENT,
            payloadParam = "requestDto"
    )
    public PatientResponseDto save(PatientRequestDto requestDto) {
        return patientService.save(requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.UPDATE,
            idParam = "patientId",
            payloadParam = "requestDto"

    )
    public PatientResponseDto updatePatient(Long patientId,PatientRequestDto requestDto){
        return patientService.update(patientId,requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.PATIENT,
            action = AccessType.DELETE,
            idParam = "patientId"
    )
    public void deletePatient(Long patientId){
        patientService.deleteById(patientId);
    }

}
