package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProductDetail;
import com.printing_shop.Enity.ProductEnity;
import com.printing_shop.Repositories.ProductDetailRepository;
import com.printing_shop.Repositories.ProductRepository;
import com.printing_shop.Service.ProductDetailService;
import com.printing_shop.dtoRequest.ProductDetailRequest;
import com.printing_shop.dtoRespose.ProductDetailResponse;
import com.printing_shop.excetion.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDetailResponse createDetail(ProductDetailRequest request) {
        ProductEnity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.getProductId()));

        ProductDetail detail = ProductDetail.builder()
                .product(product)
                .detailName(request.getAttributeName())
                .detailValue(request.getAttributeValue())
                .description(request.getDescription())
                .build();

        ProductDetail savedDetail = productDetailRepository.save(detail);
        return mapToResponse(savedDetail);
    }

    @Override
    @Transactional
    public ProductDetailResponse updateDetail(Integer id, ProductDetailRequest request) {
        ProductDetail detail = productDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detail not found with ID: " + id));

        if (request.getProductId() != null) {
            ProductEnity product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.getProductId()));
            detail.setProduct(product);
        }

        if (request.getAttributeName() != null) detail.setDetailName(request.getAttributeName());
        if (request.getAttributeValue() != null) detail.setDetailValue(request.getAttributeValue());
        if (request.getDescription() != null) detail.setDescription(request.getDescription());

        ProductDetail updated = productDetailRepository.save(detail);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDetailResponse> getDetailsByProductId(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product " + productId + " does not exist.");
        }

        return productDetailRepository.findByProduct_Id(productId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDetail(Integer id) {
        if (!productDetailRepository.existsById(id)) {
            throw new ResourceNotFoundException("Detail ID " + id + " not found.");
        }
        productDetailRepository.deleteById(id);
    }

    private ProductDetailResponse mapToResponse(ProductDetail detail) {
        ProductDetailResponse response = new ProductDetailResponse();
        response.setProductDetailId(detail.getProductDetailId());
        response.setProductId(detail.getProduct().getProductId());
        response.setAttributeName(detail.getDetailName());
        response.setAttributeValue(detail.getDetailValue());
        response.setDescription(detail.getDescription());
        return response;
    }
}