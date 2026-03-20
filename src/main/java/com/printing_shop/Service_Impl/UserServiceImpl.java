package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.User;
import com.printing_shop.Repositories.UserRepository;
import com.printing_shop.Service.JwtService;
import com.printing_shop.Service.UserService;
import com.printing_shop.dtoRequest.*;
import com.printing_shop.dtoRespose.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService; 

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone()) // Now mapping the phone field
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "CUSTOMER")
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .status(201)
                .message("Registration successful!")
                .email(user.getEmail())
                .fullName(user.getFullName())
                // If you want phone in the response, add it to RegisterResponse.java first
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. Authenticate
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2.  Find User
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate Tokens
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // 4. Return LoginResponse
        return LoginResponse.builder()
                .status(200)
                .message("Login Successful")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
    }
}