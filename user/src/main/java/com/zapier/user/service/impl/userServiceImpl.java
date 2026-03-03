package com.zapier.user.service.impl;


import com.zapier.user.dto.userDto;
import com.zapier.user.entity.User;
import com.zapier.user.repository.userRepository;
import com.zapier.user.service.userService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class userServiceImpl implements userService {

    private userRepository userRepository;

    public void createAccount(userDto user){
        User user =
        userRepository.findById()
    }
}
