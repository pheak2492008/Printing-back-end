package com.printing_shop.Service;

import com.printing_shop.dtoRequest.*;
import com.printing_shop.dtoResponse.*;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void logout();
}