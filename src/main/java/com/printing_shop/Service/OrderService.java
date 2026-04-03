package com.printing_shop.Service;

import com.printing_shop.Enity.Order;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoRespose.OrderResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request, MultipartFile file);
    OrderResponse getOrderReceipt(Long id);
    List<Order> getHistoryByPhone(String phone);
    Double calculatePrice(OrderRequest request);
    void cancelOrder(Long id);
    List<OrderResponse> getAllOrders();
}