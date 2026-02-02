package com.hospital.mediflow.Security.Dtos;

import com.hospital.mediflow.Security.Roles.Role;
import java.util.Set;

public record UserLoginResponse(
        String username,
        String accessToken,
        Role role,
        Set<String> permissions
) {
}
