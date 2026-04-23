package com.printing_shop.Controller;

import com.printing_shop.Enity.OrderItem;

import com.printing_shop.Service.OrderItemService;
import com.printing_shop.dtoRequest.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin("*")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    @PreAuthorize("isAuthenticated()") 
    public ResponseEntity<OrderItem> addItemToOrder(@RequestBody OrderItemRequest request) {
        OrderItem savedItem = orderItemService.addItemToOrder(request);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }
  
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable Long orderId) {
        // Matches the method name in your updated Service interface
        List<OrderItem> items = orderItemService.getItemsByOrderId(orderId);
        return ResponseEntity.ok(items);
    }
}