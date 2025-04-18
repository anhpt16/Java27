package com.example.day_08.repository;

import com.example.day_08.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByMovieId(Integer movieId);
    Optional<Review> findByIdAndUserId(Integer reviewId, Integer userId);
}
