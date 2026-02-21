package com.hospital.mediflow.Common.Authorization.Rules.Doctor.Read;

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
public class DoctorReadByIdPatientRule implements ActionRule {
    private final PatientDataService patientDataService;

    @Override
    public Role role() {
        return Role.DOCTOR;
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
        boolean hasRelation =  patientDataService
                .isDoctorPatientRelationExists(
                        context.getUser().getResourceId(),
                        context.getResourceId()
                );

        if(!hasRelation){
            throw new AccessDeniedException(generateRelationExceptionMessage(context.getResourceId(),role().name(),resource().name()));
        }
    }
}
