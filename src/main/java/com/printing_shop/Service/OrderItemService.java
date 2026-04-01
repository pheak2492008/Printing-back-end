package com.printing_shop.Service;

import com.printing_shop.Enity.OrderItem;
import com.printing_shop.dtoRequest.OrderItemRequest;
import java.util.List;

public interface OrderItemService {
    // This is the main method to add an item
    OrderItem addItemToOrder(OrderItemRequest request); 
    
    // This retrieves all items for a specific order
    List<OrderItem> getItemsByOrderId(Long orderId);
}