package com.example.day_08.service;

import com.example.day_08.entity.Review;
import com.example.day_08.model.request.CreateReviewRequest;
import com.example.day_08.model.request.UpdateReviewRequest;
import com.example.day_08.model.response.ReviewResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest request, Integer userId);
    Review updateReview(UpdateReviewRequest request, Integer userId);
    List<ReviewResponse> getReviews(Integer movieId);
}
