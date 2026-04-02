package com.printing_shop.Repositories;

import com.printing_shop.Enity.ProductEnity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEnity, Long> {

	Optional<ProductEnity> findById(Integer productId);

	boolean existsById(Integer productId);
}