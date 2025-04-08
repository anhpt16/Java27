package com.example.day_08.repository;

import com.example.day_08.entity.Favorite;
import com.example.day_08.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Favorite findByUserIdAndMovieId(Integer userId, Integer movieId);
    void deleteByUserId(Integer userId);
    boolean existsByUserIdAndMovieId(Integer userId, Integer movieId);

    // Lấy danh sách movie yêu thích
    @Query("SELECT f.movie FROM Favorite f WHERE f.user.id = :userId AND f.movie.status = :status")
    Page<Movie> findFavoriteMovieByUser(@Param("userId") Integer userId, @Param("status") Boolean status, Pageable pageable);

}
