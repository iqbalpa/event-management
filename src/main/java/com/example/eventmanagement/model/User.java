package com.example.eventmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String email;
    private String password;
    private int age;
    private Gender gender;
}
