package com.printing_shop.dtoResponse;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private String imageUrl;
    
    @Column(length = 1000) // Increase to 1000 characters
    private String description;
}