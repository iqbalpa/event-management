package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.model.response.DataEntity;
import com.example.eventmanagement.model.response.ErrorEntity;
import com.example.eventmanagement.model.response.GeneralResponseEntity;
import com.example.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<GeneralResponseEntity<UserEntity>> getProfile(
        @RequestBody String email
    ) {
        try {
            UserEntity user = userService.getUserDetail(email);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<UserEntity>builder()
                    .data(DataEntity.<UserEntity>builder()
                        .message("Successfully get user profile")
                        .details(user)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponseEntity.<UserEntity>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<GeneralResponseEntity<UserEntity>> updateProfile(
        @RequestBody UserEntity user
    ) {
        try {
            UserEntity updatedUser = userService.updateUser(user);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<UserEntity>builder()
                    .data(DataEntity.<UserEntity>builder()
                        .message("Successfully update user profile")
                        .details(updatedUser)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponseEntity.<UserEntity>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }
}
