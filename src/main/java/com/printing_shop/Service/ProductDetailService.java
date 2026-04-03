package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ProductDetailRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import java.util.List;

public interface ProductDetailService {
    ProductDetailResponse getDetailsByProductId(Long productId);
    void saveOrUpdateDetail(ProductDetailRequest request);
    void deleteDetail(Long productId);
}