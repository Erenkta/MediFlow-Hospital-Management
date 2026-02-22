package com.hospital.mediflow.Common.Authorization.Rules.Manager.Delete;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.MedicalRecord;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManagerDeleteMedicalRecordRule implements ActionRule {
    private final DoctorDepartmentDataService docDepDataService;
    private final MedicalRecordDataService medicalRecordDataService;

    @Override
    public Role role() {
        return Role.MANAGER;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.MEDICAL_RECORD;
    }

    @Override
    public AccessType action() {
        return AccessType.DELETE;
    }

    @Override
    public void check(AuthorizationContext context) {
        MedicalRecord record = medicalRecordDataService.findReferenceById(context.getResourceId());
        boolean hasAccess =
                docDepDataService.isDepartmentDoctorRelationsExists(
                        record.getDoctor().getId(), context.getUser().getResourceId()
                );
        if (!hasAccess) {
            throw new AccessDeniedException(generateRelationExceptionMessage(context.getResourceId(),action().name(),role().name(),resource().name()));
        }
    }
}
