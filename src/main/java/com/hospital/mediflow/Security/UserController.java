package com.hospital.mediflow.Security;

import com.hospital.mediflow.Security.Dtos.*;
import com.hospital.mediflow.Security.Roles.Permission;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.Services.Abstracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegister register){
        return ResponseEntity.ok(service.register(register));
    }
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLogin login){
        return ResponseEntity.ok(service.verify(login));
    }
    @GetMapping("/roles")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.ok(service.getRoles());
    }
    @GetMapping("/permissions")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Permission>> getPermissions(){
        return ResponseEntity.ok(service.getPermissions());
    }
    @PatchMapping("/role/assign/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRolePermissionResponse> assignRole(@RequestBody @Valid Role role, @PathVariable Long userId){
        return ResponseEntity.ok(service.assignRole(role,userId));
    }
}
