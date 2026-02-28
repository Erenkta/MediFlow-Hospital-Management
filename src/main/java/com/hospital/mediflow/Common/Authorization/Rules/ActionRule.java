package com.hospital.mediflow.Common.Authorization.Rules;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Security.Roles.Role;

public interface ActionRule {
    Role role();
    ResourceType resource();
    AccessType action();
    void check(AuthorizationContext context);

    default String generateRelationExceptionMessage(Long resourceId,String action,String subject,String resource){
        return String.format("Access is Denied. Current %s user with id : %s does not have %s access to %s. Please validate the payload.",subject,resourceId,action,resource);
    }
}

