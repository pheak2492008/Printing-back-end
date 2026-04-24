package com.printing_shop.dtoRequest;

import lombok.Data;

@Data
public class ProductDetailRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}