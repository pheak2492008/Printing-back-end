package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.*;
import com.printing_shop.Repositories.*;
import com.printing_shop.Service.OrderService;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoRespose.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

    // Use a path that works across different systems
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Override
    public Double calculatePrice(OrderRequest request) {
        Material m = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        Double price = request.getWidth() * request.getLength() * m.getPricePerSqm();
        return Math.round(price * 100.0) / 100.0;
    }

    @Override
    public Order createOrder(OrderRequest request, MultipartFile file) {
        // 1. Fetch Material - CRITICAL: If table is empty, this fails here with a clear message
        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material ID " + request.getMaterialId() + " not found. Please create a Material first!"));

        // 2. Fetch User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged in user not found in database."));

        // 3. Calculation Logic
        Double area = request.getWidth() * request.getLength();
        Double totalPrice = area * material.getPricePerSqm();
        if (Boolean.TRUE.equals(request.getHasGrommets())) totalPrice += 0.50;
        if (Boolean.TRUE.equals(request.getHasHems())) totalPrice += 0.50;
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;

        // 4. File Handling
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            Path path = Paths.get(UPLOAD_DIR);
            if (!Files.exists(path)) Files.createDirectories(path);
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }

        // 5. Save Order (using currentUser and material we found)
        Order order = Order.builder()
                .width(request.getWidth())
                .length(request.getLength())
                .totalPrice(totalPrice)
                .material(material)
                .user(currentUser) 
                .inkChoice(request.getInkChoice())
                .status("PENDING")
                .dpiQuality(request.getDpiQuality())
                .hasGrommets(request.getHasGrommets())
                .hasHems(request.getHasHems())
                .designFileUrl("/uploads/" + fileName)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrderReceipt(Long id) {
        Order o = orderRepository.findById(id).orElseThrow();
        return OrderResponse.builder()
                .orderId(o.getOrderId()).width(o.getWidth()).length(o.getLength())
                .totalPrice(o.getTotalPrice()).status(o.getStatus())
                .designFileUrl(o.getDesignFileUrl())
                .material(o.getMaterial()).user(o.getUser())
                .build();
    }

    @Override
    public List<Order> getMyHistory() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findByUserEmail(email);
    }

    @Override
    public void cancelOrder(Long id) {
        Order o = orderRepository.findById(id).orElseThrow();
        if ("PENDING".equals(o.getStatus())) {
            orderRepository.delete(o);
        }
    }
}