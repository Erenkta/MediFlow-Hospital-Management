package com.hospital.mediflow.Security.UserDetails;

import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediflowUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
    public static UserPrincipal currentPrincipal(){
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal)) {
            throw new AccessDeniedException("Unauthenticated");
        }
        return (UserPrincipal) auth.getPrincipal();
    }
    public static User currentUser() {
        return currentPrincipal().getUser();
    }
    public static Role currentUserRole(){
        return currentUser().getRole();
    }
}
