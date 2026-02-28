package com.hospital.mediflow.Common.Authorization.Model;

import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Security.Roles.Role;

public record FilterRuleKey(
        Role role,
        ResourceType resource
) {
}
