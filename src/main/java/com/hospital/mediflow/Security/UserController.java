package com.hospital.mediflow.Security;

import com.hospital.mediflow.Security.Dtos.UserLogin;
import com.hospital.mediflow.Security.Dtos.UserLoginResponse;
import com.hospital.mediflow.Security.Dtos.UserRegister;
import com.hospital.mediflow.Security.Dtos.UserRegisterResponse;
import com.hospital.mediflow.Security.Services.Abstracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
