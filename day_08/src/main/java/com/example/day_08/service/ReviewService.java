package com.example.day_08.service;

import com.example.day_08.entity.Review;
import com.example.day_08.model.request.CreateReviewRequest;
import com.example.day_08.model.request.UpdateReviewRequest;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    Review createReview(CreateReviewRequest request);
    Review updateReview(UpdateReviewRequest request);
}
