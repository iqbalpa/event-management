package com.example.eventmanagement.service;

public interface EncryptionService {
    String encrypt(String text);
    boolean matches(String rawPass, String encryptedPass);
}
