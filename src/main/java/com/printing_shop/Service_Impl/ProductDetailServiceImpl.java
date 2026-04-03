package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProductDetail;
import com.printing_shop.Enity.ProductEnity;
import com.printing_shop.Repositories.ProductDetailRepository;
import com.printing_shop.Repositories.ProductRepository;
import com.printing_shop.Service.ProductDetailService;
import com.printing_shop.dtoRequest.ProductDetailRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository detailRepo;

    @Autowired
    private ProductRepository productRepo;

    @Override
    public ProductDetailResponse getDetailsByProductId(Long productId) {
        ProductDetail detail = detailRepo.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Details not found for Product: " + productId));

        ProductDetailResponse res = new ProductDetailResponse();
        res.setProductId(detail.getProduct().getId());
        res.setProductName(detail.getProduct().getName());
        res.setPrice(detail.getProduct().getPrice());
        res.setDescription(detail.getDescription());
        res.setSpecifications(detail.getSpecifications());
        res.setMaterialList(detail.getMaterialList());
        return res;
    }

    @Override
    public void saveOrUpdateDetail(ProductDetailRequest request) {
        ProductEnity product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDetail detail = detailRepo.findByProductId(request.getProductId())
                .orElse(new ProductDetail());

        detail.setProduct(product);
        detail.setDescription(request.getDescription());
        detail.setSpecifications(request.getSpecifications());
        detail.setMaterialList(request.getMaterialList());
        
        detailRepo.save(detail);
    }

    @Override
    public void deleteDetail(Long productId) {
        ProductDetail detail = detailRepo.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Detail not found"));
        detailRepo.delete(detail);
    }
}