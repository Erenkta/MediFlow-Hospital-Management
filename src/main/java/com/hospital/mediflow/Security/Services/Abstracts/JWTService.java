package com.hospital.mediflow.Security.Services.Abstracts;

import com.hospital.mediflow.Security.Dtos.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(String username, User user);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
