package com.printing_shop.dtoRequest;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}