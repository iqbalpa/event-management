package com.example.eventmanagement.controller;

import com.example.eventmanagement.exception.RegisterException;
import com.example.eventmanagement.model.request.RegisterRequest;
import com.example.eventmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) throws RegisterException {
        return ResponseEntity.ok(authService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(authService.login(
                    request.getEmail(),
                    request.getPassword()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
}
