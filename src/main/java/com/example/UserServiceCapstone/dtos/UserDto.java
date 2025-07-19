package com.example.UserServiceCapstone.dtos;

import com.example.UserServiceCapstone.models.Role;
import com.example.UserServiceCapstone.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;

    private List<Role> roles; // Assuming role is a string, adjust as necessary

    public  static UserDto from(User user){
        if(user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;

    }

    // Additional fields can be added as needed
}
