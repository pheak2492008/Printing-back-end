package com.printing_shop.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest {

    private String title;
    private String name;
    private Double price;
    private String description;
    private Integer productId;
    private Integer stock;
}