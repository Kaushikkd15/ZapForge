package com.zapier.user.mapper;

import com.zapier.user.dto.userDto;
import com.zapier.user.entity.User;

public class UserMapper {

    public static User maptoUser(User user, userDto userDto){
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
    }
}
