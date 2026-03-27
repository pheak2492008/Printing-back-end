package com.printing_shop.dtoRespose;

import com.printing_shop.Enity.Material;
import com.printing_shop.Enity.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Double width;
    private Double length;
    private Double totalPrice;
    private String status;
    private String inkChoice;
    private String dpiQuality;
    private String designFileUrl;
    
    private Material material; 
    private User user;
}