package com.hospital.mediflow.Security.Dtos;

import com.hospital.mediflow.Security.Roles.Role;

import java.util.Set;

public record UserRolePermissionResponse(
        Long userId,
        Role role,
        Set<String> permissions
) {
}
