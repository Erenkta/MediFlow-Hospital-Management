package com.hospital.mediflow.Common.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class TraceParamFilter extends OncePerRequestFilter {
    @Value("${mediflow.detailed.exception}")
    public boolean detailedException;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws java.io.IOException, jakarta.servlet.ServletException {

        if (detailedException) {
            request.setAttribute("trace",true);
        }

        filterChain.doFilter(request, response);
    }
}