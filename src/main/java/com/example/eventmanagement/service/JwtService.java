package com.example.eventmanagement.service;

import java.util.Map;

public interface JwtService {

    String generateToken(String name, String email, String role);

    boolean validateToken(String token, String email);

    Map<String, Object> getTokenClaims(String token);
}
