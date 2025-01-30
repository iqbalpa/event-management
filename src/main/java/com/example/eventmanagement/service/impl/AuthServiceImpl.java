package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.exception.LoginException;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.AuthService;
import com.example.eventmanagement.service.EncryptionService;
import com.example.eventmanagement.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String register(String name, String email, String password) {
        // TODO: try catch
        // create token first before saving user
        String encryptedPassword = encryptionService.encrypt(password);
        UserEntity user = UserEntity.builder()
                .name(name)
                .email(email)
                .password(encryptedPassword)
                .build();
        userRepository.save(user);
        return jwtService.generateToken(user.getName(), user.getEmail());
    }

    @Override
    public String login(String email, String password) throws LoginException {
        try {
            UserEntity user = userRepository.findByEmail(email).get();
            if (encryptionService.matches(password, user.getPassword())) {
                return jwtService.generateToken(user.getName(), user.getEmail());
            }
            throw new LoginException("Invalid email or password");
        } catch (Exception e) {
            throw new LoginException("Invalid email or password", e);
        }
    }
}
