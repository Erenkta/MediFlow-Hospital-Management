package com.hospital.mediflow.Common.Authorization.Model;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Security.Roles.Role;

public record RuleKey(
        Role role,
        ResourceType resource,
        AccessType action
) {
}
