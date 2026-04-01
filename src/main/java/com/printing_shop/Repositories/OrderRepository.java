package com.printing_shop.Repositories;

import com.printing_shop.Enity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Fix for the findById/orderId issue we discussed earlier
    Optional<Order> findByOrderId(Long orderId);

    /**
     * FIX: findByUser_Email
     * This tells JPA: "Look at the 'user' field in Order, 
     * then find the 'email' field inside that User."
     */
    List<Order> findByUser_Email(String email);
}