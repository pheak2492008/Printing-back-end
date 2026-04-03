package com.printing_shop.dtoRequest;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class InventoryRequest {
    
    @NotNull(message = "Material ID is required")
    private Long materialId;

    @PositiveOrZero(message = "Total stock cannot be negative")
    private Double totalStockM2;

    @PositiveOrZero(message = "Remaining stock cannot be negative")
    private Double remainingStockM2;
}