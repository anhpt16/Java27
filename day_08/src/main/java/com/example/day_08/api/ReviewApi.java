package com.example.day_08.api;

import com.example.day_08.entity.Review;
import com.example.day_08.model.dto.UserContext;
import com.example.day_08.model.request.CreateReviewRequest;
import com.example.day_08.model.request.UpdateReviewRequest;
import com.example.day_08.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewApi {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(
        @RequestBody CreateReviewRequest request
    ) {
        Integer userId = UserContext.getUser().getId();
        Review review = reviewService.createReview(request, userId);
        if (review == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(review);
    }

    @PutMapping
    public ResponseEntity<?> updateReview(
        @RequestBody UpdateReviewRequest request
    ) {
        Integer userId = UserContext.getUser().getId();
        Review review = reviewService.updateReview(request, userId);
        if (review == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
        @PathVariable Integer reviewId
    ) {
        Integer userId = UserContext.getUser().getId();
        boolean result = reviewService.deleteReview(reviewId, userId);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
