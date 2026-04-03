package com.printing_shop.Controller;

import com.printing_shop.Service.ProductDetailService;
import com.printing_shop.dtoRequest.ProductDetailRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-detail")
@CrossOrigin("*")
public class ProductDetailController {

    @Autowired
    private ProductDetailService detailService;

    // READ - For your frontend detail page
    @PostMapping("/view")
    public ResponseEntity<ProductDetailResponse> view(@RequestBody ProductDetailRequest req) {
        return ResponseEntity.ok(detailService.getDetailsByProductId(req.getProductId()));
    }

    // CREATE / UPDATE - For Admin
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ProductDetailRequest req) {
        detailService.saveOrUpdateDetail(req);
        return ResponseEntity.ok("Detail saved successfully");
    }

    // DELETE - For Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        detailService.deleteDetail(id);
        return ResponseEntity.ok("Detail deleted");
    }
}