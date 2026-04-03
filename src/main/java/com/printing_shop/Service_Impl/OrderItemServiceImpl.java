package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.Material;
import com.printing_shop.Enity.Order;
import com.printing_shop.Enity.OrderItem;
import com.printing_shop.Repositories.MaterialRepository;
import com.printing_shop.Repositories.OrderRepository;
import com.printing_shop.Repositories.OrderItemRepository;
import com.printing_shop.Service.OrderItemService;
import com.printing_shop.dtoRequest.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public OrderItem addItemToOrder(OrderItemRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + request.orderId()));

        Material material = materialRepository.findById(request.materialId())
                .orElseThrow(() -> new RuntimeException("Material not found with ID: " + request.materialId()));

        // Area * Price calculation
        Double area = request.width() * request.length();
        Double subtotal = area * material.getPricePerM2();

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .material(material)
                .width(request.width())
                .length(request.length())
                .pricePerM2(material.getPricePerM2())
                .subtotal(subtotal)
                .build();

        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        // This calls the custom method in your OrderItemRepository
        return orderItemRepository.findByOrderOrderId(orderId);
    }
}