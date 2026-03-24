package com.printing_shop.Service;
import com.printing_shop.Enity.Order;
import com.printing_shop.dtoRequest.OrderRequest;
import com.printing_shop.dtoRespose.OrderResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface OrderService {
    Double calculatePrice(OrderRequest request);
    Order createOrder(OrderRequest request, MultipartFile file);
    List<Order> getMyHistory();
    OrderResponse getOrderReceipt(Long id);
    void cancelOrder(Long id);
}