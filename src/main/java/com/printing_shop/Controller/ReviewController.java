package com.printing_shop.Controller;

import com.printing_shop.Service.ReviewService;
import com.printing_shop.dtoRequest.ReviewRequest;
import com.printing_shop.dtoResponse.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // For the homepage summary
    @GetMapping("/summary")
    public ResponseEntity<ReviewResponse> getSummary() {
        return ResponseEntity.ok(reviewService.getReviewSummary());
    }

    // To submit a new review
    @PostMapping("/add")
    public ResponseEntity<String> postReview(@RequestBody ReviewRequest request) {
        reviewService.addReview(request);
        return ResponseEntity.ok("Review added successfully");
    }
}