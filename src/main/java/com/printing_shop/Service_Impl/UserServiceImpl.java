package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.User;
import com.printing_shop.Repositories.UserRepository;
import com.printing_shop.Service.JwtService;
import com.printing_shop.Service.UserService;
import com.printing_shop.dtoRequest.LoginRequest;
import com.printing_shop.dtoRequest.RegisterRequest;
import com.printing_shop.dtoResponse.LoginResponse;
import com.printing_shop.dtoResponse.RegisterResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "CUSTOMER")
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .status(201)
                .message("Registration successful!")
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .status(200)
                .message("Login Successful")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .user(user)
                .build();
    }
    
    @Override
    public void logout() {
        // Just clearing context. Do NOT use userRepository.delete(user) here 
        // unless you want to delete the account forever on every logout!
        SecurityContextHolder.clearContext();
        System.out.println("User logged out successfully.");
    }
}