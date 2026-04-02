package com.printing_shop.Repositories;

import com.printing_shop.Enity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findByProduct_Id(Integer id);
}