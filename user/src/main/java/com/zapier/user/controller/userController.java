package com.zapier.user.controller;

import com.zapier.user.dto.userDto;
import com.zapier.user.service.impl.userServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class userController {

    private final userServiceImpl userService;

    public userController(userServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String createAccount(@Valid @RequestBody userDto){
          userService.createAccount();
    }
}
