package com.printing_shop.dtoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialResponse {
    private Long id;
    private String name;
    private Double pricePerM2;
    private String description;
}