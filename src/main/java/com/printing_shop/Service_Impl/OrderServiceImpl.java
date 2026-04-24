package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.*;

import com.printing_shop.Repositories.*;
import com.printing_shop.Service.OrderService;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoResponse.OrderResponse;

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
    @Transactional
    public Order createOrder(OrderRequest request, MultipartFile file) {
        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        Double totalPrice = calculatePrice(request);

        String fileUrl = null;
        // ✅ Make file optional to allow description-only orders
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            try {
                Path path = Paths.get(UPLOAD_DIR);
                if (!Files.exists(path)) Files.createDirectories(path);
                Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                fileUrl = "/uploads/" + fileName;
            } catch (IOException e) {
                throw new RuntimeException("File save failed: " + e.getMessage());
            }
        }

        Order order = Order.builder()
                .customerName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .width(request.getWidth())
                .length(request.getLength())
                .inkChoice(request.getInkChoice())
                .dpiQuality(request.getDpiQuality())
                .hasGrommets(request.getHasGrommets())
                .hasHems(request.getHasHems())
                .description(request.getDescription()) // ✅ Save the text
                .totalPrice(totalPrice)
                .material(material)
                .status("PENDING")
                .designFileUrl(fileUrl)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderReceipt(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .customerName(order.getCustomerName())
                .phoneNumber(order.getPhoneNumber())
                .width(order.getWidth())
                .length(order.getLength())
                .inkChoice(order.getInkChoice())      // This will now work
                .dpiQuality(order.getDpiQuality())    // This will now work without errors
                .hasGrommets(order.getHasGrommets())  // This will now work
                .hasHems(order.getHasHems())          // This will now work
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .designFileUrl(order.getDesignFileUrl())
                .description(order.getDescription())
                .material(order.getMaterial())
                .build();
    }

    @Override
    public Double calculatePrice(OrderRequest request) {
        Material m = materialRepository.findById(request.getMaterialId()).get();
        Double area = request.getWidth() * request.getLength();
        return Math.round((area * m.getPricePerM2()) * 100.0) / 100.0;
    }

    @Override
    public List<Order> getHistoryByPhone(String phone) { return orderRepository.findByPhoneNumber(phone); }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order o = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if ("PENDING".equals(o.getStatus())) orderRepository.delete(o);
    }
}