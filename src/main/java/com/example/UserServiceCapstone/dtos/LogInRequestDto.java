package com.example.UserServiceCapstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter

@Setter
public class LogInRequestDto {
    private String email;
    private String password;

    // Additional fields can be added as needed
}
