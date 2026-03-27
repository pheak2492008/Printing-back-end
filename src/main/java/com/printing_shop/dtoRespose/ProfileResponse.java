package com.printing_shop.dtoRespose;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    private String id;
    private String name;
    private String email;
    private String image;
}