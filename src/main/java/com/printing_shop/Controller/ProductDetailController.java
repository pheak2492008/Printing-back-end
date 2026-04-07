package com.printing_shop.Controller;

import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import com.printing_shop.Service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-details")
@CrossOrigin(origins = "http://localhost:5178")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService detailService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(detailService.getById(id));
    }

    @GetMapping
    public List<ProductDetailResponse> getAll() {
        return detailService.getAll();
    }

    // Admin Only: Create or Update details
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDetailResponse> create(@RequestBody ProductRequest request) {
        return ResponseEntity.status(201).body(detailService.create(request));
    }
}