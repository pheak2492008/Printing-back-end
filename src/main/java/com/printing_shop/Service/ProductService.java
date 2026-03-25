package com.printing_shop.Service;

import com.printing_shop.Enity.ProductEnity;

import java.util.List;

public interface ProductService {
    ProductEnity create(ProductEnity product);
    List<ProductEnity> getAll();
    ProductEnity getById(Long id);
    ProductEnity update(Long id, ProductEnity product);
    void delete(Long id);
}