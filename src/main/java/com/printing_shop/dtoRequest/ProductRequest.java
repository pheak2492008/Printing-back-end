package com.printing_shop.dtoRequest;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema; // Add this import
import jakarta.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String title;
    @Column(length = 1000) // Increase to 1000 characters
    private String description;
    private Double price;
    
    @Schema(hidden = true) // 👈 This hides it from the Swagger JSON box
    private String imageUrl; 
    
    private Integer productId;
    private String name; 
    private Integer stock;
}