package com.example.UserServiceCapstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String name;
    private String password;
    private String email;



    // Additional fields can be added as needed
}
