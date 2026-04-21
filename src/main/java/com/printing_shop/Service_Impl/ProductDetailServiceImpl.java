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

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository repository;
    private final String UPLOAD_DIR = "uploads/products/";

    private ProductDetailResponse mapToResponse(ProductDetail entity) {
        return ProductDetailResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    @Override
    @Transactional
    public ProductDetailResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException {
        // 1. Save file to disk logic...
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get("uploads/products/").resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 2. Map DTO to Entity (Check every field!)
        ProductDetail entity = ProductDetail.builder()
                .title(request.getTitle())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .productId(request.getProductId())
                .stock(request.getStock())
                .imageUrl("/uploads/products/" + fileName) // The hidden field is set here
                .build();

        return mapToResponse(repository.save(entity));
    }
    @Override
    @Transactional
    public ProductDetailResponse update(Long id, ProductRequest request, MultipartFile file) throws IOException {
        ProductDetail existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Updating fields from request
        existing.setTitle(request.getTitle());
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());
        existing.setProductId(request.getProductId());

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            existing.setImageUrl("/uploads/products/" + fileName);
        }

        return mapToResponse(repository.save(existing));
    }

    @Override
    public List<ProductDetailResponse> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse getById(Long id) {
        ProductDetail entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return mapToResponse(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) { repository.deleteById(id); }

    @Override
    public ProductDetailResponse create(ProductRequest request) {
        ProductDetail entity = ProductDetail.builder()
                .name(request.getName()).description(request.getDescription())
                .price(request.getPrice()).stock(request.getStock()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public ProductDetailResponse update(Long id, ProductRequest request) {
        ProductDetail existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());
        return mapToResponse(repository.save(existing));
    }
}