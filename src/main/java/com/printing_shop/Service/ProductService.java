package com.printing_shop.Service;

import com.printing_shop.Enity.ProductEntity;
import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoResponse.ProductResponse;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    List<ProductResponse> getAll();
    ProductResponse getById(Long id); // <--- This powers your Detail Page
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException;
	List<ProductResponse> getByCategoryId(Integer categoryId);
}