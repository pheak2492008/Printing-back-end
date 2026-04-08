package com.printing_shop.dtoRespose;

import lombok.Data;

@Data
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String imageUrl; // Added this field
}