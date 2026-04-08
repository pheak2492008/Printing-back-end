package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProductDetail;
import com.printing_shop.Repositories.ProductDetailRepository;
import com.printing_shop.Service.ProductDetailService;
import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository repository;
    private final String UPLOAD_DIR = "uploads/";

    private ProductDetailResponse mapToResponse(ProductDetail entity) {
        ProductDetailResponse response = new ProductDetailResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setPrice(entity.getPrice());
        response.setStock(entity.getStock());
        response.setImageUrl(entity.getImageUrl());
        return response;
    }

    @Override
    @Transactional
    public ProductDetailResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ProductDetail entity = ProductDetail.builder()
                .name(request.getName() != null ? request.getName() : request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl("/uploads/" + fileName)
                .stock(request.getStock() != null ? request.getStock() : 10)
                .build();

        return mapToResponse(repository.save(entity));
    }

    @Override
    public ProductDetailResponse getById(Long id) {
        ProductDetail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Detail not found: " + id));
        return mapToResponse(entity);
    }

    @Override
    public List<ProductDetailResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDetailResponse create(ProductRequest request) {
        ProductDetail entity = ProductDetail.builder()
                .name(request.getName() != null ? request.getName() : request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .stock(request.getStock() != null ? request.getStock() : 0)
                .build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public ProductDetailResponse update(Long id, ProductRequest request) {
        ProductDetail existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(request.getName() != null ? request.getName() : request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());
        if(request.getImageUrl() != null) existing.setImageUrl(request.getImageUrl());

        return mapToResponse(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}