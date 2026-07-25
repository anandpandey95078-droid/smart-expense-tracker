package com.anand.expensetracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anand.expensetracker.dto.AuthResponse;
import com.anand.expensetracker.dto.LoginRequest;
import com.anand.expensetracker.entity.User;
import com.anand.expensetracker.repository.UserRepository;
import com.anand.expensetracker.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register User
    public String register(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // Login User
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        String token =
                jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}