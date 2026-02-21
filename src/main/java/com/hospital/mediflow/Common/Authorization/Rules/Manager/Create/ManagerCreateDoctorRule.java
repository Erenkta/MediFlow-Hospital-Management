package com.hospital.mediflow.Common.Authorization.Rules.Manager.Create;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Model.DoctorAccessData;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManagerCreateDoctorRule implements ActionRule {
    private final SpecialtyDataService specialtyDataService;

    @Override
    public Role role() {
        return Role.MANAGER;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.DOCTOR;
    }

    @Override
    public AccessType action() {
        return AccessType.CREATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        DoctorAccessData data = (DoctorAccessData) context.getPayload();
        boolean hasAccess = specialtyDataService.isSpecialtyDepartmentRelationExists(data.specialty(),context.getUser().getResourceId());
        if (!hasAccess) {
            throw new AccessDeniedException(generateRelationExceptionMessage(data.doctorId(),role().name(),resource().name()));
        }
    }
}
