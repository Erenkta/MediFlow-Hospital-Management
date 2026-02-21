package com.hospital.mediflow.Common.Authorization.Model;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.Getter;

@Getter
public class AuthorizationContext {

    private final User user;
    private final AccessType action;
    private final ResourceType resource;
    private final Long resourceId;
    private final Object payload;
    private final Object filter;

    public AuthorizationContext(ResourceType resource,
                                AccessType access,
                                Long resourceId,
                                User user,
                                Object payload,
                                Object filter) {

        this.action = access;
        this.resource = resource;
        this.resourceId = resourceId;
        this.user = user;
        this.payload = payload;
        this.filter = filter;
    }
}
