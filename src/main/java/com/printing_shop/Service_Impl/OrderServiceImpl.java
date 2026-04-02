package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.*;
import com.printing_shop.Repositories.*;
import com.printing_shop.Service.OrderService;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoRespose.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Override
    public Double calculatePrice(OrderRequest request) {
        Material m = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        
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
                .orElseThrow(() -> new RuntimeException("Material not found ID: " + request.getMaterialId()));
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Double area = request.getWidth() * request.getLength();
        Double totalPrice = area * material.getPricePerM2();
        if (Boolean.TRUE.equals(request.getHasGrommets())) totalPrice += 0.50;
        if (Boolean.TRUE.equals(request.getHasHems())) totalPrice += 0.50;

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            Path path = Paths.get(UPLOAD_DIR);
            if (!Files.exists(path)) Files.createDirectories(path);
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File save failed: " + e.getMessage());
        }

        Order order = Order.builder()
                .width(request.getWidth())
                .length(request.getLength())
                .inkChoice(request.getInkChoice())
                .dpiQuality(request.getDpiQuality())
                .hasGrommets(request.getHasGrommets())
                .hasHems(request.getHasHems())
                .totalPrice(Math.round(totalPrice * 100.0) / 100.0)
                .material(material)
                .user(currentUser)
                .status("PENDING")
                .designFileUrl("/uploads/" + fileName)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrderReceipt(Long id) {
        Order o = orderRepository.findByOrderId(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        return OrderResponse.builder()
                .orderId(o.getOrderId())
                .width(o.getWidth())
                .length(o.getLength())
                .totalPrice(o.getTotalPrice())
                .status(o.getStatus())
                .inkChoice(o.getInkChoice())
                .dpiQuality(o.getDpiQuality())
                .designFileUrl(o.getDesignFileUrl())
                .material(o.getMaterial())
                .user(o.getUser())
                .build();
    }

    @Override
    public List<Order> getMyHistory() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findByUser_Email(email);
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order o = orderRepository.findByOrderId(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if ("PENDING".equals(o.getStatus())) {
            orderRepository.delete(o);
        } else {
            throw new RuntimeException("Cannot delete order that is already " + o.getStatus());
        }
    }
}