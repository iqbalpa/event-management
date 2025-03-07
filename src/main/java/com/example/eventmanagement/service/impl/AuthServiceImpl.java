package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.exception.LoginException;
import com.example.eventmanagement.exception.RegisterException;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.model.request.RegisterRequest;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.AuthService;
import com.example.eventmanagement.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String register(RegisterRequest request) throws RegisterException {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RegisterException("Email already exists");
            }
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            UserEntity user = UserEntity.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .age(request.getAge())
                    .gender(request.getGender())
                    .role(request.getRole())
                    .build();
            String token = jwtService.generateToken(
                user.getName(),
                user.getEmail(),
                user.getRole().name());
            userRepository.save(user);
            return token;
        } catch (Exception e) {
            throw new RegisterException("Failed to register new user", e);
        }
    }

    @Override
    public String login(String email, String password) throws LoginException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserEntity user = userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new LoginException("Invalid email or password"));
            return jwtService.generateToken(
                user.getName(),
                user.getEmail(),
                user.getRole().name());
        } catch (Exception e) {
            throw new LoginException("Invalid email or password", e);
        }
    }
}
