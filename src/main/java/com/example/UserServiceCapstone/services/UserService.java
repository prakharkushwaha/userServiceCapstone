package com.example.UserServiceCapstone.services;

import com.example.UserServiceCapstone.exceptions.UnauthorizedException;
import com.example.UserServiceCapstone.exceptions.UserNotFoundException;
import com.example.UserServiceCapstone.models.Token;
import com.example.UserServiceCapstone.models.User;

public interface UserService {

    User signUp(String email, String password, String name);
    Token logIn(String email, String password) throws UserNotFoundException, UnauthorizedException;
     User validateToken(String tokenValue);
     void logout(String tokenValue);

}
