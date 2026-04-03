package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProductEnity;
import com.printing_shop.Repositories.ProductRepository;
import com.printing_shop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductEnity create(ProductEnity product) {
        // Force ID to null so Hibernate knows it is a NEW record
        product.setId(null); 
        return productRepository.save(product);
    }

    @Override
    public List<ProductEnity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEnity getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    @Transactional
    public ProductEnity update(Long id, ProductEnity request) {
        // 1. Fetch from DB to avoid "Stale Object" error
        ProductEnity existing = getById(id);

        // 2. Update the managed object
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setTag(request.getTag());
        existing.setTagColor(request.getTagColor());
        existing.setBgColor(request.getBgColor());
        existing.setIconBg(request.getIconBg());
        existing.setProductId(request.getProductId());

        // 3. Save the already-attached object
        return productRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}