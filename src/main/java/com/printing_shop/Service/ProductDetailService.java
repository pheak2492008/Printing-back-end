package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ProductDetailRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;

import java.util.List;

public interface ProductDetailService {
    ProductDetailResponse createDetail(ProductDetailRequest request);
    ProductDetailResponse updateDetail(Integer id, ProductDetailRequest request);
    List<ProductDetailResponse> getDetailsByProductId(Integer productId);
    void deleteDetail(Integer id);
}