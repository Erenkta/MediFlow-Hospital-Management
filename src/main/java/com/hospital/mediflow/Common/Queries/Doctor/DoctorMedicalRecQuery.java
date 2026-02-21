package com.hospital.mediflow.Common.Queries.Doctor;

import com.hospital.mediflow.Common.Annotations.Access.Doctor.AutoFillDoctorId;
import com.hospital.mediflow.Common.Annotations.Access.Doctor.DoctorRecordAccess;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Helpers.Predicate.MedicalRecordPredicateBuilder;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DoctorMedicalRecQuery {
    private final MedicalRecordService service;
    private final MedicalRecordPredicateBuilder builder;

    @AutoFillDoctorId
    public Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, MedicalRecordFilterDto filter) {
        return service.findAllMedicalRecords(pageable,builder.buildWithDto(filter));
    }

    @AutoFillDoctorId
    public List<MedicalRecordResponseDto> findAllMedicalRecords(MedicalRecordFilterDto filter) {
        return service.findAllMedicalRecords(builder.buildWithDto(filter));
    }

    @DoctorRecordAccess(type = AccessType.READ_BY_ID)
    public MedicalRecordResponseDto findMedicalRecordById(Long recordId) {
        return service.findMedicalRecordById(recordId);
    }

    @PreAuthorize("#requestDto.doctorId() == authentication.principal.resourceId")
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto requestDto) {
        return service.createMedicalRecord(requestDto);
    }

    @DoctorRecordAccess(type = AccessType.UPDATE)
    public MedicalRecordResponseDto updateMedicalRecord(Long recordId, MedicalRecordRequestDto requestDto) {
        return service.updateMedicalRecord(recordId,requestDto);
    }

}
