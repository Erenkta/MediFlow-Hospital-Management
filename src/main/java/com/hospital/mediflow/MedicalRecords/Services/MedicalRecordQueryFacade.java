package com.hospital.mediflow.MedicalRecords.Services;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.FilterManager;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Common.Helpers.Predicate.MedicalRecordPredicateBuilder;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
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
    private final MedicalRecordService service;
    private final MedicalRecordPredicateBuilder builder;

    @FilterManager(
            filterClass = MedicalRecordFilterDto.class,
            resourceType = ResourceType.MEDICAL_RECORD,
            filterParam = "filter"
    )
    public List<MedicalRecordResponseDto> getMedicalRecords(MedicalRecordFilterDto filter){
        return service.findAllMedicalRecords(builder.buildWithDto(filter));
    }

    @FilterManager(
            filterClass = MedicalRecordFilterDto.class,
            resourceType = ResourceType.MEDICAL_RECORD,
            filterParam = "filter"
    )
    public Page<MedicalRecordResponseDto> getMedicalRecords(Pageable pageable, MedicalRecordFilterDto filter){
        return service.findAllMedicalRecords(pageable,builder.buildWithDto(filter));
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
