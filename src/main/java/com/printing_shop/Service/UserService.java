package com.printing_shop.Service;

import com.printing_shop.dtoRequest.*;
import com.printing_shop.dtoRespose.AuthResponse;

public interface UserService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}