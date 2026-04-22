package com.printing_shop.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponse {
    private double averageRating; // e.g., 4.8
    private long totalReviews;    // e.g., 282
}