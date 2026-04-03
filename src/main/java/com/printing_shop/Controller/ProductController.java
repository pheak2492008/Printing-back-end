package com.printing_shop.Controller;

import com.printing_shop.Enity.ProductEnity;
import com.printing_shop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductEnity> create(@RequestBody ProductEnity product) {
        // Logic: You can add an @PreAuthorize("hasRole('ADMIN')") here later
        return ResponseEntity.status(201).body(productService.create(product));
    }

    @GetMapping
    public List<ProductEnity> getAll() {
        return productService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEnity> update(@PathVariable Long id, @RequestBody ProductEnity product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}