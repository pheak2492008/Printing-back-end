package com.printing_shop.Service;

import com.printing_shop.dtoRequest.InventoryRequest;
import com.printing_shop.dtoResponse.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest request);
    InventoryResponse updateInventory(Long inventoryId, InventoryRequest request);
    InventoryResponse getInventoryById(Long inventoryId);
    List<InventoryResponse> getAllInventories();
    void deleteInventory(Long inventoryId);
}