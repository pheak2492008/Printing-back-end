package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProductEnity;
import com.printing_shop.Repositories.ProductRepository;
import com.printing_shop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductEnity create(ProductEnity product) {
        return productRepository.save(product);
    }

    @Override
    public List<ProductEnity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEnity getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductEnity update(Long id, ProductEnity product) {
        ProductEnity existing = getById(id);

        existing.setTitle(product.getTitle());
        existing.setDescription(product.getDescription());
        existing.setTag(product.getTag());
        existing.setTagColor(product.getTagColor());
        existing.setBgColor(product.getBgColor());
        existing.setIconBg(product.getIconBg());

        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}