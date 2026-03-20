package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.User;
import com.printing_shop.Repositories.UserRepository;
import com.printing_shop.Service.UserService;
import com.printing_shop.dtoRequest.*;
import com.printing_shop.dtoRespose.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        return "Registration successful!";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse("Login Successful", user.getRole(), user.getFullName());
        }
        throw new RuntimeException("Invalid credentials");
    }
}