package com.printing_shop.Service_Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.printing_shop.Enity.ProductDetail;
import com.printing_shop.Repositories.ProductDetailRepository;
import com.printing_shop.Service.ProductDetailService;
import com.printing_shop.dtoRequest.ProductRequest;
import com.printing_shop.dtoResponse.ProductDetailResponse;
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
public class ProductServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final Cloudinary cloudinary;

    private String uploadToCloudinary(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        Map<?, ?> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "image",
                        "folder", "printing/products",
                        "use_filename", true,
                        "unique_filename", true
                )
        );

        return uploadResult.get("secure_url").toString();
    }

    private ProductDetailResponse mapToResponse(ProductDetail entity) {
        return ProductDetailResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .name(entity.getName())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .productId(entity.getProductId())
                .stock(entity.getStock())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    @Override
    @Transactional
    public ProductDetailResponse saveWithImage(ProductRequest request, MultipartFile file) throws IOException {
        String imageUrl = uploadToCloudinary(file);

        ProductDetail entity = ProductDetail.builder()
                .title(request.getTitle())
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .productId(request.getProductId())
                .stock(request.getStock())
                .imageUrl(imageUrl)
                .build();

        ProductDetail saved = productDetailRepository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ProductDetailResponse update(Long id, ProductRequest request, MultipartFile file) throws IOException {
        ProductDetail entity = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Detail not found with id: " + id));

        entity.setTitle(request.getTitle());
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setDescription(request.getDescription());
        entity.setProductId(request.getProductId());
        entity.setStock(request.getStock());

        if (file != null && !file.isEmpty()) {
            String newImageUrl = uploadToCloudinary(file);
            entity.setImageUrl(newImageUrl);
        }

        ProductDetail updated = productDetailRepository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    public List<ProductDetailResponse> getAll() {
        return productDetailRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse getById(Long id) {
        return productDetailRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Product Detail not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        productDetailRepository.deleteById(id);
    }

    @Override
    public ProductDetailResponse create(ProductRequest request) {
        return null;
    }

    @Override
    public ProductDetailResponse update(Long id, ProductRequest request) {
        return null;
    }
}