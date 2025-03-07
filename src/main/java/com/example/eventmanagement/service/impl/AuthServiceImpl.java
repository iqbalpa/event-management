package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.exception.LoginException;
import com.example.eventmanagement.exception.RegisterException;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.model.request.RegisterRequest;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.AuthService;
import com.example.eventmanagement.service.EncryptionService;
import com.example.eventmanagement.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private EncryptionService encryptionService;
    private UserRepository userRepository;
    private JwtService jwtService;

    @Autowired
    public AuthServiceImpl(
            EncryptionService encryptionService,
            UserRepository userRepository,
            JwtService jwtService
    ) {
        this.encryptionService = encryptionService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String register(RegisterRequest request) throws RegisterException {
        try {
            String encryptedPassword = encryptionService.encrypt(request.getPassword());
            UserEntity user = UserEntity.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(encryptedPassword)
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
            Optional<UserEntity> user = userRepository.findByEmail(email);
            if (user.isPresent() && encryptionService.matches(password, user.get().getPassword())) {
                return jwtService.generateToken(
                    user.get().getName(),
                    user.get().getEmail(),
                    user.get().getRole().name());
            }
            throw new LoginException("Invalid email or password");
        } catch (Exception e) {
            throw new LoginException("Invalid email or password", e);
        }
    }
}
