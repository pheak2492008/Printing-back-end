package com.printing_shop.dtoRespose;

import lombok.Data;

@Data
public class ProductDetailResponse {
    private Integer productDetailId;
    private Integer productId;
    private String attributeName;
    private String attributeValue;
    private String description;
}