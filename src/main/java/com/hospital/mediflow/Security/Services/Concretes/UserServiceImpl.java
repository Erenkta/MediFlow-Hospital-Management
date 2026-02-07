package com.hospital.mediflow.Security.Services.Concretes;

import com.hospital.mediflow.Common.Exceptions.MediflowAuthException;
import com.hospital.mediflow.Mappers.UserMapper;
import com.hospital.mediflow.Security.Dtos.*;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Security.Roles.Permission;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.Services.Abstracts.JWTService;
import com.hospital.mediflow.Security.Services.Abstracts.UserService;
import com.hospital.mediflow.Security.UserDetails.Repository.UserRepository;
import com.hospital.mediflow.Security.UserDetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    @Override
    @PreAuthorize("hasAuthority('manager:create')")
    @Transactional
    public UserRegisterResponse register(UserRegister register) {
        User user = userRepository.findByUsername(register.username());
        if(user != null){
            throw new MediflowAuthException("Username is already taken");
        }
        return mapper.toRegisterResponse(userRepository.save(mapper.toUser(register)));
    }

    @Override
    public UserLoginResponse verify(UserLogin login){
         try{
             Authentication auth =
                     authManager.authenticate(new UsernamePasswordAuthenticationToken(login.username(),login.password()));
             UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
             User currentUser = principal.getUser();
             if(auth.isAuthenticated()){
                 String accessToken = jwtService.generateToken(login.username(),currentUser);
                 return new UserLoginResponse(currentUser.getUsername(),
                         accessToken,
                         currentUser.getResourceId(),
                         currentUser.getRole(),
                         currentUser.getRole().getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).filter(item -> !item.contains("ROLE_")).collect(Collectors.toSet()));
             }
             throw new BadCredentialsException("Invalid username or password. Please try again with different credentials.");
         }catch (AuthenticationException ex){
             throw new MediflowAuthException(ex.getMessage());
         }
    }

    @Override
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Role> getRoles() {
        return Arrays.stream(Role.values()).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Permission> getPermissions() {
        return Arrays.stream(Permission.values()).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('admin:patch')")
    @Transactional
    public UserRolePermissionResponse assignRole(Role role, Long userId) {
        User user = findUser(userId);
        user.setRole(role);
        userRepository.save(user);
        return new UserRolePermissionResponse(userId,user.getRole(),user.getRole().getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).filter(item -> !item.contains("ROLE_")).collect(Collectors.toSet()));
    }

    private User findUser(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> {
            String errorMessage = String.format("User with given id not found : %s",userId);
            return new UsernameNotFoundException(errorMessage);
        });
    }
}
