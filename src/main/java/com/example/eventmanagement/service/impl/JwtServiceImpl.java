package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;
    private static final long EXPIRATION_TIME = 1000L * 60L * 30L; // 30 minutes

    @Override
    public String generateToken(String name, String email, String role) {
        Map<String, Object> claims = Map.of(
                "name", name,
                "email", email,
                "role", role
        );
        return createToken(claims, email);
    }

    @Override
    public boolean validateToken(String token, String email) {
        Map<String, Object> claims = getTokenClaims(token);
        String emailFromToken = (String) claims.get("email");
        return !isTokenExpired(token) && email.equals(emailFromToken);
    }

    @Override
    public Map<String, Object> getTokenClaims(String token) {
        return extractAllClaims(token);
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(generateSigningKey())
                .compact();
    }

    private Key generateSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(generateSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
