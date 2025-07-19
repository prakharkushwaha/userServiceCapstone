package com.example.UserServiceCapstone.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends Exception {


    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
