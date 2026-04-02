package com.printing_shop.dtoRequest;

import lombok.Data;

@Data
public class ProductDetailRequest {
    private Integer productId;
    private String attributeName;
    private String attributeValue;
    private String description;
}