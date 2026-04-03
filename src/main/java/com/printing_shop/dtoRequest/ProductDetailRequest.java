package com.printing_shop.dtoRequest;

import lombok.Data;

@Data
public class ProductDetailRequest {
    private Long productId;
    private String description;
    private String specifications;
    private String materialList;
}