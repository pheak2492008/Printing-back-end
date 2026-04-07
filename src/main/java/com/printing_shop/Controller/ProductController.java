package com.printing_shop.Controller;

import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoRespose.ProductResponse;
import com.printing_shop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5178"})
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponse> createWithImage(
            @RequestPart("file") MultipartFile file, 
            @RequestPart("request") ProductRequest request // Changed from @ModelAttribute or @RequestBody
    ) throws IOException {
        return ResponseEntity.ok(productService.saveWithImage(request, file));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}