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


    default String generateAccessExceptionMessage(AccessType action,String subject){
        return String.format("Invalid access type for %s user. Access Type : %s",subject,action.name());
    }
    default String generateRelationExceptionMessage(Long resourceId,String action,String subject,String resource){
        return String.format("Access is Denied. Current %s user does not have %s access to %s with id : %s",subject,action,resource,resourceId);
    }
}

