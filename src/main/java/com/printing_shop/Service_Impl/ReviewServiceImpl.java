package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.Review;
import com.printing_shop.Repositories.ReviewRepository;
import com.printing_shop.Service.ReviewService;
import com.printing_shop.dtoRequest.ReviewRequest;
import com.printing_shop.dtoResponse.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void addReview(ReviewRequest request) {
        Review review = new Review();
        review.setProductId(request.getProductId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        reviewRepository.save(review);
    }

    @Override
    public ReviewResponse getReviewSummary() {
        List<Review> reviews = reviewRepository.findAll();
        
        long count = reviews.size();
        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        // Formats to match your UI (e.g., 4.8)
        double formattedAverage = Math.round(average * 10.0) / 10.0;
        
        return new ReviewResponse(formattedAverage, count);
    }
}