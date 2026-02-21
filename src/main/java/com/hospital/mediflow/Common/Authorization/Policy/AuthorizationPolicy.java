package com.hospital.mediflow.Common.Authorization.Policy;

import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Security.Roles.Role;

public interface AuthorizationPolicy {
    boolean supports(Role role);
    void check(AuthorizationContext context);
}
