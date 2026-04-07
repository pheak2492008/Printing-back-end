package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductDetailService {
    ProductDetailResponse create(ProductRequest request);
    List<ProductDetailResponse> getAll();
    ProductDetailResponse getById(Long id);
    ProductDetailResponse update(Long id, ProductRequest request); // Fix return type
    void delete(Long id);
	ProductDetailResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException;
}