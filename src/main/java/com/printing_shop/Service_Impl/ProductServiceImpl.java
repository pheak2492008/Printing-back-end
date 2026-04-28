package com.printing_shop.Service_Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.printing_shop.Enity.ProductEntity;
import com.printing_shop.Repositories.ProductRepository;
import com.printing_shop.Service.ProductService;
import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoResponse.ProductResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Cloudinary cloudinary; // Inject the Cloudinary bean

    private ProductResponse mapToResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl()) // This will now be a https:// link
                .productId(entity.getProductId())
                .build();
    }

    /**
     * NEW: Uploads file to Cloudinary and returns the Secure URL
     */
    private String uploadToCloudinary(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
        
        // Upload to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
                ObjectUtils.asMap("resource_type", "auto"));
        
        // Return the permanent HTTPS URL
        return uploadResult.get("secure_url").toString();
    }

    @Override
    @Transactional
    public ProductResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException {
        // Use Cloudinary instead of local disk
        String imageUrl = uploadToCloudinary(file);

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

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setProductId(request.getProductId());

        // Update image via Cloudinary ONLY if a new file is provided
        if (file != null && !file.isEmpty()) {
            String newImageUrl = uploadToCloudinary(file);
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