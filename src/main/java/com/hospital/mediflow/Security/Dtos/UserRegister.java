package com.hospital.mediflow.Security.Dtos;

import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import com.hospital.mediflow.Security.Roles.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRegister(
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @NotNull
        @ValidateEnum(enumClass = Role.class,message = "Invalid role value")
        Role role,
        @NotNull
        Long resourceId
) {
}
