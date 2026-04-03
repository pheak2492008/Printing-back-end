package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.*;
import com.printing_shop.Repositories.*;
import com.printing_shop.Service.OrderService;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoRespose.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MaterialRepository materialRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Override
    public Double calculatePrice(OrderRequest request) {
        Material m = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        
        // Math: Area * Price + Add-ons
        Double area = request.getWidth() * request.getLength();
        Double price = area * m.getPricePerM2();
        
        if (Boolean.TRUE.equals(request.getHasGrommets())) price += 0.50;
        if (Boolean.TRUE.equals(request.getHasHems())) price += 0.50;
        
        return Math.round(price * 100.0) / 100.0;
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequest request, MultipartFile file) {
        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material ID not found: " + request.getMaterialId()));

        Double totalPrice = calculatePrice(request);

        // Save File to local folder
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            Path path = Paths.get(UPLOAD_DIR);
            if (!Files.exists(path)) Files.createDirectories(path);
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File save failed: " + e.getMessage());
        }

        // Build and Save the Entity
        Order order = Order.builder()
                .customerName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .width(request.getWidth())
                .length(request.getLength())
                .inkChoice(request.getInkChoice())
                .dpiQuality(request.getDpiQuality())
                .hasGrommets(request.getHasGrommets())
                .hasHems(request.getHasHems())
                .totalPrice(totalPrice)
                .material(material)
                .status("PENDING")
                .designFileUrl("/uploads/" + fileName)
                .build();

        return orderRepository.save(order);
    }
    
    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        
        // Map each Order entity to an OrderResponse
        return orders.stream().map(o -> OrderResponse.builder()
                .orderId(o.getOrderId())
                .customerName(o.getCustomerName())
                .phoneNumber(o.getPhoneNumber())
                .width(o.getWidth())
                .length(o.getLength())
                .totalPrice(o.getTotalPrice())
                .status(o.getStatus())
                .inkChoice(o.getInkChoice())
                .dpiQuality(o.getDpiQuality())
                .designFileUrl(o.getDesignFileUrl())
                .material(o.getMaterial())
                .build()
        ).toList();
    }

    @Override
    public OrderResponse getOrderReceipt(Long id) {
        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderResponse.builder()
                .orderId(o.getOrderId())
                .customerName(o.getCustomerName())
                .phoneNumber(o.getPhoneNumber())
                .width(o.getWidth())
                .length(o.getLength())
                .totalPrice(o.getTotalPrice())
                .status(o.getStatus())
                .inkChoice(o.getInkChoice())
                .dpiQuality(o.getDpiQuality())
                .designFileUrl(o.getDesignFileUrl())
                .material(o.getMaterial())
                .build();
    }

    @Override
    public List<Order> getHistoryByPhone(String phone) {
        return orderRepository.findByPhoneNumber(phone);
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order o = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if ("PENDING".equals(o.getStatus())) {
            orderRepository.delete(o);
        } else {
            throw new RuntimeException("Cannot cancel order that is already in " + o.getStatus() + " state.");
        }
    }
}