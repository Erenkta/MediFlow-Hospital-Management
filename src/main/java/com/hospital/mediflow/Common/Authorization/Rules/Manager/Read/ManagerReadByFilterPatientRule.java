package com.hospital.mediflow.Common.Authorization.Rules.Manager.Read;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ManagerReadByFilterPatientRule implements ActionRule {
    private final DoctorDepartmentDataService doctorDepartmentDataService;
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
        return AccessType.READ_BY_FILTER;
    }

    @Override
    public void check(AuthorizationContext context) {
        PatientFilterDto filter = ((PatientFilterDto)context.getFilter());
        if(filter.doctorId() != null &&
                !doctorDepartmentDataService.isDepartmentDoctorRelationsExists(filter.doctorId(),context.getUser().getResourceId())){
            throw new AccessDeniedException(generateRelationExceptionMessage(context.getUser().getResourceId(),action().name(),context.getUser().getRole().name(),ResourceType.PATIENT.name()));
        }
    }
}