package com.hospital.mediflow.Common.Authorization.Policy.Doctor;

import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Policy.AuthorizationPolicy;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorPolicy implements AuthorizationPolicy {
    @Override
    public boolean supports(Role role) {
        return role.equals(Role.DOCTOR);
    }

    @Override
    public void check(AuthorizationContext context) {

    }
}
