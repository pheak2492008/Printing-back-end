package com.printing_shop.Service;

import java.util.List; 
import com.printing_shop.Enity.Review; 
import com.printing_shop.dtoRequest.ReviewRequest;
import com.printing_shop.dtoResponse.ReviewResponse;

public interface ReviewService {
    void addReview(ReviewRequest request);
    ReviewResponse getReviewSummary();
    List<Review> getAllReviews(); 
}