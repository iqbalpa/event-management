package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {
    private BCryptPasswordEncoder encoder;

    @Autowired
    public EncryptionServiceImpl(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encrypt(String text) {
        return encoder.encode(text);
    }

    @Override
    public boolean matches(String rawPass, String encryptedPass) {
        return encoder.matches(rawPass, encryptedPass);
    }
}
