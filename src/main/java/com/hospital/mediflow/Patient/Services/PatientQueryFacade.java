package com.hospital.mediflow.Patient.Services;

import com.hospital.mediflow.Common.Queries.Doctor.DoctorPatientQuery;
import com.hospital.mediflow.Common.Queries.Manager.ManagerPatientQuery;
import com.hospital.mediflow.Common.Queries.Patient.PatientQuery;
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
    private final PatientQuery patientQuery;
    private final PatientService patientService;

    public List<PatientResponseDto> findPatient(PatientFilterDto filter) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR  -> doctorQuery.findPatient(filter);
            case MANAGER -> managerQuery.findPatient(filter);
            case ADMIN   -> patientService.findAll(filter);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }
    public Page<PatientResponseDto> findPatient(Pageable pageable, PatientFilterDto filter) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR  -> doctorQuery.findPatient(pageable,filter);
            case MANAGER -> managerQuery.findPatient(pageable,filter);
            case ADMIN   -> patientService.findAll(pageable,filter);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }
    public PatientResponseDto findPatientById(Long patientId) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR  -> doctorQuery.findPatientById(patientId);
            case MANAGER -> managerQuery.findPatientById(patientId);
            case PATIENT -> patientQuery.findPatientById(patientId);
            case ADMIN   -> patientService.findById(patientId);
        };
    }

    public PatientResponseDto save(PatientRequestDto requestDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> patientService.save(requestDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");

        };
    }
    public PatientResponseDto updatePatient(Long patientId,PatientRequestDto requestDto){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case MANAGER -> managerQuery.updatePatient(patientId,requestDto);
            case PATIENT -> patientQuery.updatePatient(patientId,requestDto);
            case ADMIN   -> patientService.update(patientId,requestDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }
    public void deletePatient(Long patientId){
        Role role = MediflowUserDetailsService.currentUserRole();
         switch (role) {
             case PATIENT -> patientQuery.deletePatient(patientId);
             case MANAGER -> managerQuery.deletePatient(patientId);
             case ADMIN  -> patientService.deleteById(patientId);
             default -> throw new AccessDeniedException("Unsupported role for the method");
        }
    }

}
