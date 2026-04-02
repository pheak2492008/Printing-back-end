package com.printing_shop.Repositories;

import com.printing_shop.Enity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<Order> findByOrderId(Long orderId);

 
    List<Order> findByPhoneNumber(String phoneNumber);

    List<Order> findByCustomerNameContainingIgnoreCase(String name);
    
    // RMOVED: findByUser_Email (Since the 'User' relationship was removed from Order)
}