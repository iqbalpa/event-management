package com.example.eventmanagement.filter;

import com.example.eventmanagement.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer ";
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;

    @Autowired
    public JwtAuthFilter(
        HandlerExceptionResolver handlerExceptionResolver,
        JwtService jwtService
    ) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        // Skip filter for auth endpoints
        String requestPath = request.getServletPath();
        if (requestPath.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authToken = request.getHeader(AUTH_HEADER_KEY);
        Map<String, Object> claims = jwtService.getTokenClaims(authToken);
        String role = (String) claims.get("role");

        // create, update, and delete event endpoints are only accessible to admin
        List<String> restrictedMethod = List.of("POST", "PUT", "DELETE");
        if (requestPath.startsWith("/api/events/")
            && restrictedMethod.contains(request.getMethod())
            && !role.equals("ADMIN")) {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                new Exception("Unauthorized access")
            );
            return;
        }

        if (authToken == null || !authToken.startsWith(AUTH_HEADER_VALUE_PREFIX)) {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                new Exception("Invalid token")
            );
            return;
        }

        String token = authToken.substring(AUTH_HEADER_VALUE_PREFIX.length());
        if (jwtService.validateToken(token)) {
            filterChain.doFilter(request, response);
        } else {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                new Exception("Invalid token")
            );
        }
    }
}
