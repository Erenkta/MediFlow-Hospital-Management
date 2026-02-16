package com.hospital.mediflow.Common.Providers.Concretes;

import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityCurrentUserProvider implements CurrentUserProvider {
    @Override
    public User get() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new AccessDeniedException("Unauthenticated");
        }
        return user;
    }
}
