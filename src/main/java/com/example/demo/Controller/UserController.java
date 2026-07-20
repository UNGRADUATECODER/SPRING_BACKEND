package com.example.demo.Controller;

import com.example.demo.Dto.UserResponseDto;
import com.example.demo.Entity.User;
import com.example.demo.Security.CustomUserDetails;
import com.example.demo.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponseDto me() {
        return userService.getCurrentUser();
    }
}
