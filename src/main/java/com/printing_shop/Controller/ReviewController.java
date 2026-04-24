package com.printing_shop.Controller;

import java.util.List; // FIX: Use java.util.List, NOT com.sun.tools.javac.util.List
import com.printing_shop.Enity.Review; // FIX: Import your Review entity
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

    @GetMapping("/summary")
    public ResponseEntity<ReviewResponse> getSummary() {
        return ResponseEntity.ok(reviewService.getReviewSummary());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() { // Line 33 should now work
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PostMapping("/add")
    public ResponseEntity<String> postReview(@RequestBody ReviewRequest request) {
        reviewService.addReview(request);
        return ResponseEntity.ok("Review added successfully");
    }
}