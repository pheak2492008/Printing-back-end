package com.printing_shop.dtoResponse;

import com.printing_shop.Enity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private int status;
    private String message;
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private User user; 
}