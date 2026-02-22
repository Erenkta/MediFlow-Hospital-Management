package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerRecordAccess;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Helpers.Predicate.MedicalRecordPredicateBuilder;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Services.Abstracts.MedicalRecordService;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ManagerMedicalReqQuery {
    private final MedicalRecordService service;
    private final MedicalRecordPredicateBuilder filterBuilder;

    public Page<MedicalRecordResponseDto> findAllMedicalRecords(Pageable pageable, MedicalRecordFilterDto medicalRecordFilter) {
        Long departmentId = MediflowUserDetailsService.currentUser().getResourceId();
        Predicate withDepartmentFiltered = ExpressionUtils.allOf(
                filterBuilder.buildWithDto(medicalRecordFilter),
                filterBuilder.departmentScope(departmentId)
        );
        return service.findAllMedicalRecords(pageable,withDepartmentFiltered);
    }
    public List<MedicalRecordResponseDto> findAllMedicalRecords(MedicalRecordFilterDto medicalRecordFilter) {
        Long departmentId = MediflowUserDetailsService.currentUser().getResourceId();
        Predicate withDepartmentFiltered = ExpressionUtils.allOf(
                filterBuilder.buildWithDto(medicalRecordFilter),
                filterBuilder.departmentScope(departmentId)
        );
        return service.findAllMedicalRecords(withDepartmentFiltered);
    }
}
