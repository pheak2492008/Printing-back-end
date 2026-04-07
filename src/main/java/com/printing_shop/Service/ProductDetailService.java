package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import java.util.List;

public interface ProductDetailService {
    List<ProductDetailResponse> getAll();
    ProductDetailResponse getById(Long id);
    ProductDetailResponse create(ProductRequest request);
    ProductDetailResponse update(Long id, ProductRequest request);
    void delete(Long id);
}