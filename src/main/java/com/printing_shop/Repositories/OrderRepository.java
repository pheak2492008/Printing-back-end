package com.printing_shop.Repositories;

import com.printing_shop.Enity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEmail(String email);
}