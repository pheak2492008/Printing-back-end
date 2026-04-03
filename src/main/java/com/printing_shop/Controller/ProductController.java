package com.printing_shop.Controller;

import com.printing_shop.Enity.ProductEnity;
import com.printing_shop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    // CREATE
    @PostMapping
    public ProductEnity create(@RequestBody ProductEnity product) {
        return productService.create(product);
    }

    // GET ALL
    @GetMapping
    public List<ProductEnity> getAll() {
        return productService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ProductEnity getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductEnity update(@PathVariable Long id, @RequestBody ProductEnity product) {
        return productService.update(id, product);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "Product deleted successfully";
    }
}