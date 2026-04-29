package com.printing_shop.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {

    private Long id;
    private String title;
    private String name;
    private Double price;
    private String description;
    private Integer productId;
    private Integer stock;
    private String imageUrl;
}