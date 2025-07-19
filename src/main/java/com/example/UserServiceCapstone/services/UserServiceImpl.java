package com.example.UserServiceCapstone.services;

import com.example.UserServiceCapstone.exceptions.UnauthorizedException;
import com.example.UserServiceCapstone.exceptions.UserNotFoundException;
import com.example.UserServiceCapstone.models.Token;
import com.example.UserServiceCapstone.models.User;
import com.example.UserServiceCapstone.repositories.TokenRepository;
import com.example.UserServiceCapstone.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository; // Assume this is injected or initialized elsewhere
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
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
//        we are using BryptPasswordEncoder for password hashing
        newUser.setPassword(passwordEncoder.encode(password));
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
        if (passwordEncoder.matches(password, user.getPassword())) {
//            -------log in successfull ,create Toke------
            Token token = new Token();
            token.setUser(user);

//          Set expiry date 30 days from now
            Date now = new Date(); // current time
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days
            Date expiryDate = calendar.getTime(); // Get the updated date

            token.setExpiryDate(expiryDate);



//            set token
            token.setValue(RandomStringUtils.randomAlphanumeric(128)); // Generate a random token value
            return tokenRepository.save(token);

        }

//    login failed
        throw new UnauthorizedException("Login failed");



    }

    @Override
    public User validateToken(String tokenValue) {
        //Check if the token is present in the DB, token is NOT deleted and
        //token's expiry time is greater than the current time.
        Optional<Token> optionalToken = tokenRepository.
                findByValueAndDeletedAndExpiryDateGreaterThan(
                        tokenValue,
                        false,
                        new Date()
                );

        //Token invalid
        return optionalToken.map(Token::getUser).orElse(null);
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
