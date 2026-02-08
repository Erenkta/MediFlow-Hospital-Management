package com.hospital.mediflow.MedicalRecords.Services;

import com.hospital.mediflow.Common.Helpers.Predicate.MedicalRecordPredicateBuilder;
import com.hospital.mediflow.Common.Queries.Doctor.DoctorMedicalRecQuery;
import com.hospital.mediflow.Common.Queries.Manager.ManagerMedicalReqQuery;
import com.hospital.mediflow.Common.Queries.Patient.PatientMedicalRecQuery;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordQueryFacade {
    private final ManagerMedicalReqQuery managerQuery;
    private final DoctorMedicalRecQuery doctorQuery;
    private final PatientMedicalRecQuery patientQuery;
    private final MedicalRecordService service;
    private final MedicalRecordPredicateBuilder builder;

    public List<MedicalRecordResponseDto> getMedicalRecords(MedicalRecordFilterDto filter){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> service.findAllMedicalRecords(builder.buildWithDto(filter));
            case MANAGER -> managerQuery.findAllMedicalRecords(filter);
            case DOCTOR  -> doctorQuery.findAllMedicalRecords(filter);
            case PATIENT -> patientQuery.findAllMedicalRecords(filter);
        };
    }
    public Page<MedicalRecordResponseDto> getMedicalRecords(Pageable pageable, MedicalRecordFilterDto filter){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> service.findAllMedicalRecords(pageable,builder.buildWithDto(filter));
            case MANAGER -> managerQuery.findAllMedicalRecords(pageable,filter);
            case DOCTOR  -> doctorQuery.findAllMedicalRecords(pageable,filter);
            case PATIENT -> patientQuery.findAllMedicalRecords(pageable,filter);
        };
    }
    public MedicalRecordResponseDto getMedicalRecordById(Long recordId) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> service.findMedicalRecordById(recordId);
            case MANAGER -> managerQuery.findMedicalRecordById(recordId);
            case DOCTOR  -> doctorQuery.findMedicalRecordById(recordId);
            case PATIENT -> patientQuery.findMedicalRecordById(recordId);
        };
    }
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto requestDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> service.createMedicalRecord(requestDto);
            case MANAGER -> managerQuery.createMedicalRecord(requestDto);
            case DOCTOR  -> doctorQuery.createMedicalRecord(requestDto);
            default -> throw new AccessDeniedException("Access is denied");
        };
    }
    public MedicalRecordResponseDto updateMedicalRecord(Long recordId, MedicalRecordRequestDto requestDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> service.updateMedicalRecord(recordId,requestDto);
            case MANAGER -> managerQuery.updateMedicalRecord(recordId,requestDto);
            case DOCTOR  -> doctorQuery.updateMedicalRecord(recordId,requestDto);
            default -> throw new AccessDeniedException("Access is denied");
        };
    }
    public void deleteMedicalRecord(Long recordId) {
        Role role = MediflowUserDetailsService.currentUserRole();
         switch (role) {
            case ADMIN   -> service.deleteMedicalRecord(recordId);
            case MANAGER -> managerQuery.deleteMedicalRecord(recordId);
            default -> throw new AccessDeniedException("Access is denied");
        };
    }
}
