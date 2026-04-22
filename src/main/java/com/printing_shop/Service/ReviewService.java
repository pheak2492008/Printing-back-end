package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ReviewRequest;
import com.printing_shop.dtoResponse.ReviewResponse;

public interface ReviewService {
    void addReview(ReviewRequest request);
    ReviewResponse getReviewSummary();
}