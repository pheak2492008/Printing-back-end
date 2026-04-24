package com.printing_shop.Service;

import com.printing_shop.Enity.OrderItem;
import com.printing_shop.dtoRequest.OrderItemRequest;
import java.util.List;

public interface OrderItemService {
    OrderItem addItemToOrder(OrderItemRequest request);
    List<OrderItem> getItemsByOrderId(Long orderId);
    List<OrderItem> getAllOrderItems(); 
}