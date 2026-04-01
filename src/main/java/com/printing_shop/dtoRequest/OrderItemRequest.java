package com.printing_shop.dtoRequest;

import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
    @NotNull(message = "Order ID is required")
    Long orderId,
    
    @NotNull(message = "Material ID is required")
    Long materialId,
    
    @NotNull(message = "Width is required")
    Double width,
    
    @NotNull(message = "Length is required")
    Double length
) {}