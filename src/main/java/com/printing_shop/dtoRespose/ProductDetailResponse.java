package com.printing_shop.dtoRespose;

import lombok.Data;

@Data
public class ProductDetailResponse {
    private Long productId;
    private String productName;
    private Double price;
    private String description;
    private String specifications;
    private String materialList;
}