package com.printing_shop.dtoRequest;

import lombok.*;

@Data
@NoArgsConstructor  
@AllArgsConstructor
@Builder
public class ProfileRequest {
    private String fullName;
    private String phone;
    private String address;
}