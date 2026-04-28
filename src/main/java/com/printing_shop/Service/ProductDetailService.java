package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoResponse.ProductDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductDetailService {

    ProductDetailResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException;

    List<ProductDetailResponse> getAll();

    ProductDetailResponse getById(Long id);

    void delete(Long id);

    ProductDetailResponse update(Long id, ProductRequest request, MultipartFile file) throws IOException;

    // Legacy methods (keep for compatibility)
    ProductDetailResponse create(ProductRequest request);
    ProductDetailResponse update(Long id, ProductRequest request);
}