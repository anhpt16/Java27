package com.example.day_08.service.impl;

import com.example.day_08.entity.Movie;
import com.example.day_08.entity.Review;
import com.example.day_08.entity.User;
import com.example.day_08.model.request.CreateReviewRequest;
import com.example.day_08.model.request.UpdateReviewRequest;
import com.example.day_08.model.response.ReviewResponse;
import com.example.day_08.repository.MovieRepository;
import com.example.day_08.repository.ReviewRepository;
import com.example.day_08.repository.UserRepository;
import com.example.day_08.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest request, Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user có id: " + userId));

        Movie movie = movieRepository.findByIdAndStatusTrue(request.getMovieId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy moive có id: " + request.getMovieId()));

        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);
        review.setRating(request.getRating());
        review.setContent(request.getContent());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);
        if (review.getId() == null) {
            throw new RuntimeException("Lưu bình luận thất bại userId/movieId: " + userId + "/" + request.getMovieId());
        }
        return review;
    }

    @Override
    public Review updateReview(UpdateReviewRequest request, Integer userId) {
        Review review = reviewRepository.findById(request.getReviewId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy reviewId: " + request.getReviewId()));

        if (userId.intValue() != review.getUser().getId().intValue()) {
            throw new RuntimeException("Không có quyền sửa bình luận userId/reviewId: " + userId + "/" + request.getReviewId());
        }

        review.setContent(request.getContent());
        review.setRating(request.getRating());
        review.setUpdatedAt(LocalDateTime.now());
        Review updateReview = reviewRepository.save(review);

        if (updateReview.getId() == null) {
            throw new RuntimeException("Cập nhật thất bại userId/reviewId: " + userId + "/" + request.getReviewId());
        }
        return review;
    }

    @Override
    public boolean deleteReview(Integer reviewId, Integer userId) {
        Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy reviewId/userId: " + reviewId + "/" + userId));
        reviewRepository.delete(review);
        if (reviewRepository.findById(reviewId).isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public List<ReviewResponse> getReviews(Integer movieId) {
        Movie movie = movieRepository.findByIdAndStatusTrue(movieId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy movieId: " + movieId));
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        if (reviews.isEmpty()) {
            return Collections.emptyList();
        }
        return reviews.stream()
            .map(review -> ReviewResponse.builder()
                    .id(review.getId())
                    .userId(review.getUser().getId())
                    .name(review.getUser().getDisplayName())
                    .rating(review.getRating())
                    .content(review.getContent())
                    .date(review.getUpdatedAt())
                    .build())
            .collect(Collectors.toList());
    }
}
