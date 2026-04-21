package com.printing_shop.Controller;

import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import com.printing_shop.Service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-details")
@CrossOrigin(origins = "http://localhost:5173") // Fixed to match your React port
@RequiredArgsConstructor
public class ProductDetailController {

    // The variable is named detailService
    private final ProductDetailService detailService;

    @GetMapping
    public List<ProductDetailResponse> getAll() {
        return detailService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(detailService.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDetailResponse> create(
            @RequestPart("request") ProductRequest request, 
            @RequestPart("file") MultipartFile file) throws IOException {
        
        return ResponseEntity.status(201).body(detailService.saveWithImage(request, file));
    }
   
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')") // Only users with the ADMIN role can access this
    public ResponseEntity<ProductDetailResponse> update(
            @PathVariable Long id,
            @RequestPart("request") ProductRequest request, 
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        
        return ResponseEntity.ok(detailService.update(id, request, file));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}