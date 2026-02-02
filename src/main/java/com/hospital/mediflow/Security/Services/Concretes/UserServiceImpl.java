package com.hospital.mediflow.Security.Services.Concretes;

import com.hospital.mediflow.Common.Exceptions.MediflowAuthException;
import com.hospital.mediflow.Mappers.UserMapper;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Security.Dtos.UserLogin;
import com.hospital.mediflow.Security.Dtos.UserLoginResponse;
import com.hospital.mediflow.Security.Dtos.UserRegister;
import com.hospital.mediflow.Security.Dtos.UserRegisterResponse;
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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

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
    @PreAuthorize("hasRole('MANAGER')")
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
                 String accessToken = jwtService.generateToken(login.username());
                 return new UserLoginResponse(currentUser.getUsername(),
                         accessToken,currentUser.getRole(),
                         currentUser.getRole().getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).filter(item -> !item.contains("ROLE_")).collect(Collectors.toSet()));
             }
             throw new BadCredentialsException("Invalid username or password. Please try again with different credentials.");
         }catch (AuthenticationException ex){
             throw new MediflowAuthException(ex.getMessage());
         }
    }
}
