package com.hospital.mediflow.Common.Authorization.Rules.Manager.Update;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Model.DoctorAccessData;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManagerUpdateDoctorRule implements ActionRule {
    private final DoctorDepartmentDataService docDepDataService;

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
        return AccessType.UPDATE;
    }

    @Override
    public void check(AuthorizationContext context) {
        DoctorAccessData data = (DoctorAccessData)context.getPayload();
        boolean hasAccess =  docDepDataService.isDepartmentDoctorRelationsExists(data.doctorId(),context.getUser().getResourceId());
        if (!hasAccess) {
            throw new AccessDeniedException(generateRelationExceptionMessage(data.doctorId(),action().name(),role().name(),resource().name()));
        }
    }
}
