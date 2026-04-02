package com.printing_shop.dtoRespose;

import com.printing_shop.Enity.Material;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String customerName; // New
    private String phoneNumber;  // New
    private Double width;
    private Double length;
    private Double totalPrice;
    private String status;
    private String inkChoice;
    private String dpiQuality;
    private String designFileUrl;
    
    private Material material; 
    // Removed: private User user;
}