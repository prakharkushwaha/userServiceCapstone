package com.example.UserServiceCapstone.services;

import com.example.UserServiceCapstone.exceptions.UnauthorizedException;
import com.example.UserServiceCapstone.exceptions.UserNotFoundException;
import com.example.UserServiceCapstone.models.Token;
import com.example.UserServiceCapstone.models.User;
import com.example.UserServiceCapstone.repositories.TokenRepository;
import com.example.UserServiceCapstone.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository; // Assume this is injected or initialized elsewhere
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    // Implementation of UserService methods
    @Override
    public User signUp(String email, String password, String name) {
        // Implementation for user sign-up
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return existingUser.get(); // User already exists, return existing user
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password); // Consider hashing the password before saving
        newUser.setName(name);
        return userRepository.save(newUser); // Replace with actual implementation
    }

    @Override
    public Token logIn(String email, String password) throws UserNotFoundException, UnauthorizedException {
        // Implementation for user login
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            // User not found, return null or throw an exception
            throw new UserNotFoundException("user with email " + email + " not found");
        }
//        if user is present
        User user = userOptional.get();
        if (user.getPassword().equals(password)) {
//            -------log in successfull ,create Toke------
            Token token = new Token();
            token.setUser(user);

//          Set expiry date 30 days from now
            LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);
            token.setExpiryDate(expiryDate);

//            set token
            token.setValue("kjsdffhkerigfiuwrbbbk");
            return tokenRepository.save(token);

        }

//    login failed
        throw new UnauthorizedException("Login failed");



    }

    @Override
    public User validateToken(String tokenValue) {
        // Implementation for token validation
        return null; // Replace with actual implementation
    }

    @Override
    public void logout(String tokenValue) {
        // Implementation for user logout
        Optional<Token> tokenOptional = tokenRepository.findByValue(tokenValue);
        if(tokenOptional.isEmpty()){
            throw new RuntimeException("Token not found");
        }
        Token token = tokenOptional.get();
        token.setDeleted(true); // Assuming you have a 'deleted' field in Token to mark it as invalid
        tokenRepository.save(token); // Save the updated token to mark it as deleted


    }
}
