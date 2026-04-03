package com.printing_shop.Controller;

import com.printing_shop.Service.ProductDetailService;
import com.printing_shop.dtoRequest.ProductDetailRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-details")
@CrossOrigin(origins = "*")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @PostMapping("/create")
    public ResponseEntity<ProductDetailResponse> createDetail(@RequestBody ProductDetailRequest request) {
        return ResponseEntity.ok(productDetailService.createDetail(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDetailResponse> updateDetail(
            @PathVariable Integer id,
            @RequestBody ProductDetailRequest request) {
        return ResponseEntity.ok(productDetailService.updateDetail(id, request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductDetailResponse>> getByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(productDetailService.getDetailsByProductId(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        productDetailService.deleteDetail(id);
        return ResponseEntity.ok("Product detail removed successfully");
    }
}