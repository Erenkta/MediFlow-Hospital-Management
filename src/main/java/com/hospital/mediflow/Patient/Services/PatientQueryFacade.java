package com.hospital.mediflow.Patient.Services;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Common.Queries.Doctor.DoctorPatientQuery;
import com.hospital.mediflow.Common.Queries.Manager.ManagerPatientQuery;
import com.hospital.mediflow.Common.Specifications.PatientSpecification;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Services.Abstracts.PatientService;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PatientQueryFacade {

    private final DoctorPatientQuery doctorQuery;
    private final ManagerPatientQuery managerQuery;
    private final PatientService patientService;

    public List<PatientResponseDto> findPatient(PatientFilterDto filter) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR  -> doctorQuery.findPatient(filter);
            case MANAGER -> managerQuery.findPatient(filter);
            case ADMIN   -> patientService.findAll(PatientSpecification.hasFilter(filter));
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public Page<PatientResponseDto> findPatient(Pageable pageable, PatientFilterDto filter) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR  -> doctorQuery.findPatient(pageable,filter);
            case MANAGER -> managerQuery.findPatient(pageable,filter);
            case ADMIN   -> patientService.findAll(pageable,PatientSpecification.hasFilter(filter));
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
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
