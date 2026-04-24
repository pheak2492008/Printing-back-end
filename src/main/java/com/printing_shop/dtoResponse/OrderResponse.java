package com.printing_shop.dtoResponse;

import com.printing_shop.Enity.Material;

import lombok.*;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private String customerName;
    private String phoneNumber;
    private Double width;
    private Double length;
    private String inkChoice;  
    private String dpiQuality; // CHANGED from Integer to String to match Entity
    private Boolean hasGrommets; 
    private Boolean hasHems;     
    private Double totalPrice;
    private String status;
    private String designFileUrl;
    private String description;
    private Material material;
}