package com.hospital.mediflow.Common.Authorization.Rules.Doctor.Update;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DoctorUpdateMedicalRecordRule implements ActionRule {
    private final MedicalRecordDataService medicalRecordDataService;

    @Override
    public Role role() {
        return Role.DOCTOR;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.MEDICAL_RECORD;
    }

    @Override
    public AccessType action() {
        return AccessType.UPDATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        boolean isAccessible = medicalRecordDataService.isDoctorRecordRelationExists(context.getResourceId(),context.getUser().getResourceId());
        if(!isAccessible){
            throw new AccessDeniedException(generateRelationExceptionMessage(context.getResourceId(),role().name(),resource().name()));
        }
    }
}
