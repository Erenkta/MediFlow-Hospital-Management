package com.hospital.mediflow.MedicalRecords.Services;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
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

    @ResourceAccess(
            resource = ResourceType.MEDICAL_RECORD,
            action = AccessType.READ_BY_ID,
            idParam = "recordId"
    )
    public MedicalRecordResponseDto getMedicalRecordById(Long recordId) {
        return  service.findMedicalRecordById(recordId);
    }

    @ResourceAccess(
            resource = ResourceType.MEDICAL_RECORD,
            action = AccessType.CREATE,
            payloadParam = "requestDto"
    )
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto requestDto) {
        return service.createMedicalRecord(requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.MEDICAL_RECORD,
            action = AccessType.UPDATE,
            idParam = "recordId",
            payloadParam = "requestDto"
    )
    public MedicalRecordResponseDto updateMedicalRecord(Long recordId, MedicalRecordRequestDto requestDto) {
        return service.updateMedicalRecord(recordId,requestDto);
    }

    @ResourceAccess(
            resource = ResourceType.MEDICAL_RECORD,
            action = AccessType.DELETE,
            idParam = "recordId"
    )
    public void deleteMedicalRecord(Long recordId) {
        service.deleteMedicalRecord(recordId);
    }
}
