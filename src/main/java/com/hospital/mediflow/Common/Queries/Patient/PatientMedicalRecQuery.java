package com.hospital.mediflow.Common.Queries.Patient;

import com.hospital.mediflow.Common.Annotations.Access.Patient.AutoFillPatientId;
import com.hospital.mediflow.Common.Annotations.Access.Patient.PatientRecordAccess;
import com.hospital.mediflow.Common.Helpers.Predicate.MedicalRecordPredicateBuilder;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PatientMedicalRecQuery {
    private final MedicalRecordService service;
    private final MedicalRecordPredicateBuilder builder;
    @AutoFillPatientId
    public Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, MedicalRecordFilterDto filter) {
        return service.findAllMedicalRecords(pageable, builder.buildWithDto(filter));
    }

    @AutoFillPatientId
    public List<MedicalRecordResponseDto> findAllMedicalRecords(MedicalRecordFilterDto filter) {
        return service.findAllMedicalRecords(builder.buildWithDto(filter));
    }
    @PatientRecordAccess
    public MedicalRecordResponseDto findMedicalRecordById(Long recordId) {
        return service.findMedicalRecordById(recordId);
    }

}
