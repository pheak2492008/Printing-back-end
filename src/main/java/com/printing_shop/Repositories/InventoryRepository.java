package com.printing_shop.Repositories;

import com.printing_shop.Enity.Inventory; // Ensure this matches your Entity filename
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}