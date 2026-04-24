package com.printing_shop.Service_Impl;

import com.printing_shop.Repositories.InventoryRepository;
import com.printing_shop.Repositories.MaterialRepository;
import com.printing_shop.Service.InventoryService;
import com.printing_shop.dtoRequest.InventoryRequest;
import com.printing_shop.dtoResponse.InventoryResponse;
import com.printing_shop.Enity.Inventory;
import com.printing_shop.Enity.Material;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MaterialRepository materialRepository; // Fixed: Added injection

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {
        // 1. Fetch real Material data
        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found with id: " + request.getMaterialId()));

        // 2. Map Material details into Inventory so they aren't null in Swagger
        Inventory inventory = Inventory.builder()
                .materialId(material.getId())
                .materialName(material.getName())
                .materialPricePerM2(material.getPricePerM2())
                .materialDescription(material.getDescription() != null ? material.getDescription() : "No description available")
                .totalStockM2(request.getTotalStockM2())
                .remainingStockM2(request.getRemainingStockM2())
                .build();

        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse updateInventory(Long inventoryId, InventoryRequest request) {
        Inventory existing = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        existing.setMaterialId(material.getId());
        existing.setMaterialName(material.getName());
        existing.setMaterialPricePerM2(material.getPricePerM2());
        existing.setMaterialDescription(material.getDescription());
        existing.setTotalStockM2(request.getTotalStockM2());
        existing.setRemainingStockM2(request.getRemainingStockM2());

        return mapToResponse(inventoryRepository.save(existing));
    }

    @Override
    public InventoryResponse getInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return mapToResponse(inventory);
    }

    @Override
    public List<InventoryResponse> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInventory(Long inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getId())
                .materialId(inventory.getMaterialId())
                .materialName(inventory.getMaterialName())
                .materialPricePerM2(inventory.getMaterialPricePerM2())
                .materialDescription(inventory.getMaterialDescription())
                .totalStockM2(inventory.getTotalStockM2())
                .remainingStockM2(inventory.getRemainingStockM2())
                .build();
    }
}