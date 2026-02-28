package com.hospital.mediflow.Common.Authorization.Model;

import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import lombok.Getter;

@Getter
public class FilterManagerContext {

    private final User user;
    private final ResourceType resource;
    private final Object filter;

    public FilterManagerContext(ResourceType resource,
                                User user,
                                Object filter) {

        this.user = user;
        this.resource = resource;
        this.filter = filter;
    }
}
