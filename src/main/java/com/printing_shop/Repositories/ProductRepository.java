package com.printing_shop.Repositories;

import com.printing_shop.Enity.ProductEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEnity, Long> {
}