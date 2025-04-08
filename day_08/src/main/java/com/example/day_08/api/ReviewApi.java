package com.example.day_08.api;

import com.example.day_08.entity.Review;
import com.example.day_08.model.request.CreateReviewRequest;
import com.example.day_08.model.request.UpdateReviewRequest;
import com.example.day_08.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/review")
@AllArgsConstructor
public class ReviewApi {
    private final ReviewService reviewService;

    @PutMapping
    public ResponseEntity<?> createReview(
        @RequestBody CreateReviewRequest request
        ) {
        Review review = reviewService.createReview(request);
        if (review == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(review);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateReview(
        @PathVariable Integer id,
        @RequestBody UpdateReviewRequest request
    ) {
        Review review = reviewService.updateReview(request);
        if (review == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(review);
    }
}
