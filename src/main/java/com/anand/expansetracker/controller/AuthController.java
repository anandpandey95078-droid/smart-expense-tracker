package com.anand.expansetracker.controller;

import org.springframework.web.bind.annotation.*;

import com.anand.expansetracker.dto.AuthResponse;
import com.anand.expansetracker.dto.LoginRequest;
import com.anand.expansetracker.dto.RegisterRequest;
import com.anand.expansetracker.entity.User;
import com.anand.expansetracker.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}