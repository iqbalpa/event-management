package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.User;
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
    public ResponseEntity<GeneralResponseEntity<User>> getProfile(
        @RequestBody String email
    ) {
        try {
            User user = userService.getUserDetail(email);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<User>builder()
                    .data(DataEntity.<User>builder()
                        .message("Successfully get user profile")
                        .details(user)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponseEntity.<User>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }
}
