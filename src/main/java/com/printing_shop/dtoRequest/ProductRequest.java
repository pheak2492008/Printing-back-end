package com.printing_shop.dtoRequest;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema; // Add this import

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String title;
    private String description;
    private Double price;
    
    @Schema(hidden = true) // 👈 This hides it from the Swagger JSON box
    private String imageUrl; 
    
    private Integer productId;
    private String name; 
    private Integer stock;
}