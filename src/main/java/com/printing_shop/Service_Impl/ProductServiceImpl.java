package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProductEntity;
import com.printing_shop.Repositories.ProductRepository;
import com.printing_shop.Service.ProductService;
import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoResponse.ProductResponse;

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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final String UPLOAD_DIR = "uploads/";

    private ProductResponse mapToResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .productId(entity.getProductId())
                .build();
    }

    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // 1. Create Folder if it doesn't exist
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        // 2. Generate unique filename
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        
        // 3. Save to local disk
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName;
    }

    @Override
    @Transactional
    public ProductResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException {
        String imageUrl = saveImage(file);

        ProductEntity entity = ProductEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(imageUrl)
                .productId(request.getProductId())
                .build();

        return mapToResponse(productRepository.save(entity));
    }
    
    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request, MultipartFile file) throws IOException {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // 1. Update text fields
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setProductId(request.getProductId());

        // 2. Update image ONLY if a new one is provided
        if (file != null && !file.isEmpty()) {
            String newImageUrl = saveImage(file);
            entity.setImageUrl(newImageUrl);
        }

        return mapToResponse(productRepository.save(entity));
    }
    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    
    @Override
    public List<ProductResponse> getByCategoryId(Integer categoryId) {
        // We use the repository to find entities, then map them to Response DTOs
        return productRepository.findByProductId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getById(Long id) {
        return productRepository.findById(id).map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override public void delete(Long id) { productRepository.deleteById(id); }
    @Override public ProductResponse create(ProductRequest request) { return null; } 
    @Override public ProductResponse update(Long id, ProductRequest request) { return null; }
}
