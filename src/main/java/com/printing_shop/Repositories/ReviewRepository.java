package com.printing_shop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.printing_shop.Enity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}