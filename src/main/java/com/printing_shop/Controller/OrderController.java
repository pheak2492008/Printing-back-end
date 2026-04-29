package com.printing_shop.Controller;

import com.printing_shop.Enity.Order;
import com.printing_shop.Service.OrderService;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoResponse.OrderResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders") // Added /v1 to match SecurityConfig permitAll
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows all origins to prevent CORS errors during testing
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping("/calculate")
    public ResponseEntity<Double> calculate(@RequestBody OrderRequest req) {
        return ResponseEntity.ok(orderService.calculatePrice(req));
    }

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<OrderResponse> createOrder(
            @RequestParam("fullName") String fullName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("width") Double width,
            @RequestParam("length") Double length,
            @RequestParam("materialId") Long materialId,
            @RequestParam("inkChoice") String inkChoice,
            @RequestParam("dpiQuality") String dpiQuality,
            @RequestParam("hasGrommets") Boolean hasGrommets,
            @RequestParam("hasHems") Boolean hasHems,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        OrderRequest request = new OrderRequest(
            fullName, 
            phoneNumber, 
            width, 
            length, 
            materialId, 
            inkChoice, 
            dpiQuality, 
            hasGrommets, 
            hasHems, 
            description 
        );

        Order savedOrder = orderService.createOrder(request, file);
        return ResponseEntity.ok(orderService.getOrderReceipt(savedOrder.getOrderId()));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/history/{phone}")
    public ResponseEntity<List<Order>> getHistory(@PathVariable String phone) {
        return ResponseEntity.ok(orderService.getHistoryByPhone(phone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}