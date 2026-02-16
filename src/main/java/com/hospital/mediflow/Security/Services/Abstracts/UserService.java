package com.hospital.mediflow.Security.Services.Abstracts;

import com.hospital.mediflow.Security.Dtos.*;
import com.hospital.mediflow.Security.Roles.Permission;
import com.hospital.mediflow.Security.Roles.Role;

import java.util.List;
import java.util.Set;

public interface UserService {

     UserRegisterResponse register(UserRegister register);
     UserLoginResponse verify(UserLogin login);

    List<Role> getRoles();
    List<Permission> getPermissions();
    UserRolePermissionResponse assignRole(Role role, Long userId);
}
