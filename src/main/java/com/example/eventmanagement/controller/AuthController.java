package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.request.RegisterRequest;
import com.example.eventmanagement.model.response.*;
import com.example.eventmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<GeneralResponseEntity<RegisterResponse>> register(
        @RequestBody RegisterRequest request
    ) {
        try {
            String token = authService.register(request);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GeneralResponseEntity.<RegisterResponse>builder()
                    .data(DataEntity.<RegisterResponse>builder()
                        .message("Successfully register user")
                        .details(RegisterResponse.builder()
                            .token(token)
                            .build())
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponseEntity.<RegisterResponse>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponseEntity<LoginResponse>> login(
        @RequestBody RegisterRequest request
    ) {
        try {
            String token = authService.login(
                request.getEmail(),
                request.getPassword()
            );
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<LoginResponse>builder()
                    .data(DataEntity.<LoginResponse>builder()
                        .message("Login success")
                        .details(LoginResponse.builder()
                            .token(token)
                            .build())
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponseEntity.<LoginResponse>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }
}
