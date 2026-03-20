package com.printing_shop.dtoRespose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String role;
    private String name;
}