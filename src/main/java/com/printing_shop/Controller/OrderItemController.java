package com.printing_shop.Controller;

import com.printing_shop.Enity.OrderItem;

import com.printing_shop.Service.OrderItemService;
import com.printing_shop.dtoRequest.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin("*")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    /**
     * POST /api/order-items
     * We use the ID inside the RequestBody for better consistency with your Service logic.
     */
    @PostMapping
    public ResponseEntity<OrderItem> addItemToOrder(@RequestBody OrderItemRequest request) {
        // The Service now handles finding the Order and the Material
        OrderItem savedItem = orderItemService.addItemToOrder(request);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    /**
     * GET /api/order-items/order/{orderId}
     * Retrieves all items belonging to a specific order.
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable Long orderId) {
        // Matches the method name in your updated Service interface
        List<OrderItem> items = orderItemService.getItemsByOrderId(orderId);
        return ResponseEntity.ok(items);
    }
}