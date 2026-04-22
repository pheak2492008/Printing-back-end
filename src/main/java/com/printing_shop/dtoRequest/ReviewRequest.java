package com.printing_shop.dtoRequest;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long productId;
    private int rating;     // 1-5 stars
    private String comment;
}