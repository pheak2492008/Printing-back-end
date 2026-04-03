package com.printing_shop.Controller;

import com.printing_shop.Service.UserService;
import com.printing_shop.dtoRequest.*;
import com.printing_shop.dtoRespose.LoginResponse;
import com.printing_shop.dtoRespose.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // Good practice to use constructor injection
@Tag(name = "Authentication", description = "Endpoints for User Registration, Login, and Logout")
public class AuthController {

    private final UserService userService;

    @Operation
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(userService.register(request));
    }

    @Operation
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")
    @Operation
    public ResponseEntity<Map<String, String>> logout() {
        userService.logout();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out and data deleted. You must register again to access the site.");
        return ResponseEntity.ok(response);
    }
}