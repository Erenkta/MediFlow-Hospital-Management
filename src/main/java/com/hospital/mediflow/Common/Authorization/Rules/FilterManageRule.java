package com.hospital.mediflow.Common.Authorization.Rules;

import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.FilterManagerContext;
import com.hospital.mediflow.Security.Roles.Role;

public interface FilterManageRule {
    Role role();
    ResourceType resource();
    Object manageFilter(FilterManagerContext context);

}

