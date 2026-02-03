package com.hospital.mediflow.Security.Config;

import com.hospital.mediflow.Security.Filters.JWTFilter;
import com.hospital.mediflow.Security.Roles.Permission;
import com.hospital.mediflow.Security.Roles.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity // Annotation based security
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // CSRF is disabled now
                .authorizeHttpRequests(a -> a
                        //UserController
                        .requestMatchers("/api/v1/users/login")
                        .permitAll()
                        //
                        .requestMatchers("/api/v1/billings/**")
                        .hasAnyRole(Role.MANAGER.name(),Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/doctors").hasAnyAuthority(Permission.ADMIN_READ.name())
                        //
                        .anyRequest().authenticated()
                ) // Any request should be authenticated.
                .httpBasic(Customizer.withDefaults()) //
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // use jwtFitler before the UsernamePasswordAuthenticationFilter
        return http.build();
    }
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public RoleHierarchy roleHierarchy() {
        String hierarchy =
                // 1. Rollerin Kendi Arasındaki Hiyerarşisi
                "ROLE_ADMIN > ROLE_MANAGER \n" +
                        "ROLE_MANAGER > ROLE_DOCTOR \n" +
                        "ROLE_MANAGER > ROLE_PATIENT \n" +

                        // 2. ADMIN Yetki Atamaları (Admin zaten Manager'ı kapsadığı için Manager yetkilerini alır)
                        "ROLE_ADMIN > admin:read \n" +
                        "ROLE_ADMIN > admin:update \n" +
                        "ROLE_ADMIN > admin:create \n" +
                        "ROLE_ADMIN > admin:delete \n" +
                        "ROLE_ADMIN > admin:patch \n" +

                        // 3. MANAGER Yetki Atamaları
                        "ROLE_MANAGER > manager:read \n" +
                        "ROLE_MANAGER > manager:update \n" +
                        "ROLE_MANAGER > manager:create \n" +
                        "ROLE_MANAGER > manager:delete \n" +
                        "ROLE_MANAGER > manager:patch \n" +

                        // 4. DOCTOR Yetki Atamaları
                        "ROLE_DOCTOR > doctor:read \n" +
                        "ROLE_DOCTOR > doctor:update \n" +
                        "ROLE_DOCTOR > doctor:create \n" +
                        "ROLE_DOCTOR > doctor:delete \n" +
                        "ROLE_DOCTOR > doctor:patch \n" +

                        // 5. PATIENT Yetki Atamaları
                        "ROLE_PATIENT > patient:read \n" +
                        "ROLE_PATIENT > patient:update \n" +
                        "ROLE_PATIENT > patient:create \n" +
                        "ROLE_PATIENT > patient:delete \n" +
                        "ROLE_PATIENT > patient:patch";
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(5));// to use password encoder.
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
