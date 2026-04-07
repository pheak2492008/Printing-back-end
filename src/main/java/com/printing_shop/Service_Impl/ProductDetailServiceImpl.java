package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProductDetail;
import com.printing_shop.Repositories.ProductDetailRepository;
import com.printing_shop.Service.ProductDetailService;
import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository repository;

    @Override
    public List<ProductDetailResponse> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse getById(Long id) {
        ProductDetail product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return mapToResponse(product);
    }

    @Override
    public ProductDetailResponse create(ProductRequest req) {
        ProductDetail product = new ProductDetail();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        return mapToResponse(repository.save(product));
    }

    @Override
    public ProductDetailResponse update(Long id, ProductRequest req) {
        ProductDetail product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        return mapToResponse(repository.save(product));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ProductDetailResponse mapToResponse(ProductDetail entity) {
        ProductDetailResponse res = new ProductDetailResponse();
        res.setId(entity.getId());
        res.setName(entity.getName());
        res.setDescription(entity.getDescription());
        res.setPrice(entity.getPrice());
        res.setStock(entity.getStock());
        return res;
    }
}