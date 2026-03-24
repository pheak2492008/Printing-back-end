package com.printing_shop.Controller;

import com.printing_shop.Enity.Order;

import com.printing_shop.Service.OrderService;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoRespose.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping("/calculate") // API 1: Calculate
    public ResponseEntity<Double> calculate(@RequestBody OrderRequest req) {
        return ResponseEntity.ok(orderService.calculatePrice(req));
    }

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<OrderResponse> createOrder(
            @ModelAttribute OrderRequest request, 
            @RequestParam("file") MultipartFile file) throws IOException {

        Order savedOrder = orderService.createOrder(request, file);
        return ResponseEntity.ok(orderService.getOrderReceipt(savedOrder.getOrderId()));
    }
    @GetMapping("/getall") // API 3: History
    public ResponseEntity<List<Order>> history() {
        return ResponseEntity.ok(orderService.getMyHistory());
    }

    @GetMapping("/{id}") // API 4: Receipt
    public ResponseEntity<OrderResponse> receipt(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderReceipt(id));
    }

    @DeleteMapping("/{id}") // API 5: Delete
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}