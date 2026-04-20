package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.Material;
import com.printing_shop.Enity.Order;
import com.printing_shop.Enity.OrderItem;
import com.printing_shop.Repositories.MaterialRepository;
import com.printing_shop.Repositories.OrderRepository;
import com.printing_shop.Repositories.OrderItemRepository;
import com.printing_shop.Service.OrderItemService;
import com.printing_shop.dtoRequest.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final MaterialRepository materialRepository;

    @Override
    @Transactional
    public OrderItem addItemToOrder(OrderItemRequest request) {
        // 1. Fetch Order and Material based on IDs in the RequestBody
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Material material = materialRepository.findById(request.materialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        // 2. Calculation Logic based on ERD (Area * Price per M2)
        Double pricePerM2 = material.getPricePerM2();
        Double subtotal = (request.width() * request.length()) * pricePerM2;

        // 3. Build the Entity
        OrderItem item = OrderItem.builder()
                .order(order)
                .material(material)
                .width(request.width())
                .length(request.length())
                .pricePerM2(pricePerM2)
                .subtotal(subtotal)
                .build();

        OrderItem saved = orderItemRepository.save(item);

        // 4. Update the parent Order total price
        updateOrderTotal(order);

        return saved;
    }

    @Override
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrder_OrderId(orderId);
    }

    private void updateOrderTotal(Order order) {
        // Recalculate the sum of all items in this order
        Double newTotal = orderItemRepository.findByOrder_OrderId(order.getOrderId())
                .stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
        order.setTotalPrice(newTotal);
        orderRepository.save(order);
    }
}