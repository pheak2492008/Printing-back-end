package com.printing_shop.dtoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    private Integer userId;
    private String fullName;
    private String phone;
    private String address;
    private String avatarUrl;
}