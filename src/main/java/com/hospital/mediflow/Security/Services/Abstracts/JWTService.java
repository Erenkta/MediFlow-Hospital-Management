package com.hospital.mediflow.Security.Services.Abstracts;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    public String generateToken(String username);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
