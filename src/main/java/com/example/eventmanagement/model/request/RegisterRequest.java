package com.example.eventmanagement.model.request;

import com.example.eventmanagement.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private int age;
    private UserEntity.Gender gender;
    private UserEntity.Role role;
}
