package com.printing_shop.dtoRespose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private Long inventoryId;
    private Long materialId;
    private String materialName;
    private Double materialPricePerM2;
    private String materialDescription;
    private Double totalStockM2;
    private Double remainingStockM2;
}