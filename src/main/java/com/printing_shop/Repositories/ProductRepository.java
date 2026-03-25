package com.printing_shop.Repositories;

import com.printing_shop.Enity.ProductEnity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEnity, Long> {
}