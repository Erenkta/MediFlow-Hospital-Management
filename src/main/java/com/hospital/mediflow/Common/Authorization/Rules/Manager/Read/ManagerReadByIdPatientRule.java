package com.hospital.mediflow.Common.Authorization.Rules.Manager.Read;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManagerReadByIdPatientRule implements ActionRule {
    private final PatientDataService patientDataService;

    @Override
    public Role role() {
        return Role.MANAGER;
    }

    @Override
    public ResourceType resource() {
        return ResourceType.PATIENT;
    }

    @Override
    public AccessType action() {
        return AccessType.READ_BY_ID;
    }

    @Override
    public void check(AuthorizationContext context) {
        boolean hasAccess =
                patientDataService.isDepartmentPatientRelationExists(
                        context.getUser().getResourceId(),context.getResourceId()
                );

        if (!hasAccess) {
            throw new AccessDeniedException(generateRelationExceptionMessage(context.getResourceId(),action().name(),role().name(),resource().name()));
        }
    }
}
