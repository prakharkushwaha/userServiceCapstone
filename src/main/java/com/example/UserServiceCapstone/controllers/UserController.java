package com.example.UserServiceCapstone.controllers;

import com.example.UserServiceCapstone.dtos.LogInRequestDto;
import com.example.UserServiceCapstone.dtos.LogOutRequestDto;
import com.example.UserServiceCapstone.dtos.SignUpRequestDto;
import com.example.UserServiceCapstone.dtos.UserDto;
import com.example.UserServiceCapstone.exceptions.UnauthorizedException;
import com.example.UserServiceCapstone.exceptions.UserNotFoundException;
import com.example.UserServiceCapstone.models.Token;
import com.example.UserServiceCapstone.models.User;
import com.example.UserServiceCapstone.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public Token login( @RequestBody LogInRequestDto loginRequestDto) throws UserNotFoundException, UnauthorizedException {
        // Logic for user login

        Token token = userService.logIn(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );
        return token; // Return a Token object after successful login

    }

    @PostMapping("/signup")
    public UserDto signUp( @RequestBody SignUpRequestDto signUpRequestDto) {
        // Logic for user signup
        User user = userService.signUp(


                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword(),
                signUpRequestDto.getName()
        );

     // Convert the User object to UserDto before returning
        return UserDto.from(user);
    }
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(LogOutRequestDto logOutRequestDto ) {
        // Logic for user logout
       userService.logout(logOutRequestDto.getTokenvalue());

       return new ResponseEntity<>(
               HttpStatus.OK
       );


    }
    @GetMapping("/validate/{tokenValue}")
    public  UserDto validateToken( @PathVariable String  tokenValue) {
        // Logic to validate the token
        User user = userService.validateToken(tokenValue);
        // Check if the token is valid, not expired, etc.


        return UserDto.from(user);
    }

}
