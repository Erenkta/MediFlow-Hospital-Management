package com.hospital.mediflow.Common.Authorization.Rules.Doctor.Read;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DoctorReadByFilterPatientRule implements ActionRule {

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
        return AccessType.READ_BY_FILTER;
    }

    @Override
    public void check(AuthorizationContext context) {

    }
}