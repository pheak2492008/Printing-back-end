package com.printing_shop.Controller;

import com.printing_shop.Enity.Material;
import com.printing_shop.Repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin("*") 
public class MaterialController {

    private final MaterialRepository materialRepository;

    @GetMapping("/getall") // Fixed: Added quotes and removed space
    public ResponseEntity<List<Material>> getAllMaterials() {
        return ResponseEntity.ok(materialRepository.findAll());
    }

    @PostMapping("/create") // Fixed: Added quotes
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        return ResponseEntity.status(201).body(materialRepository.save(material));
    }
}