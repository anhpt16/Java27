package com.example.day_08.service.impl;

import com.example.day_08.entity.Movie;
import com.example.day_08.entity.Review;
import com.example.day_08.entity.User;
import com.example.day_08.model.request.CreateReviewRequest;
import com.example.day_08.model.request.UpdateReviewRequest;
import com.example.day_08.repository.MovieRepository;
import com.example.day_08.repository.ReviewRepository;
import com.example.day_08.repository.UserRepository;
import com.example.day_08.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest request) {
        Integer userId = 1;

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user có id: " + userId));

        Movie movie = movieRepository.findByIdAndStatusTrue(request.getMovieId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy moive có id: " + request.getMovieId()));

        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);
        review.setRating(review.getRating());
        review.setContent(review.getContent());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);
        if (review.getId() == null) {
            throw new RuntimeException("Lưu bình luận thất bại userId/movieId: " + userId + "/" + request.getMovieId());
        }
        return review;
    }

    @Override
    public Review updateReview(UpdateReviewRequest request) {
        Integer userId = 1;

        Review review = reviewRepository.findById(request.getReviewId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy review_id: " + request.getReviewId()));

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
}
