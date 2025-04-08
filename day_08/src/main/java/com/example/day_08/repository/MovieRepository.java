package com.example.day_08.repository;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.MovieType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m WHERE m.slug = :slug AND (:status IS NULL OR m.status = :status)")
    Optional<Movie> findBySlug(@Param("slug") String slug, @Param("status") Boolean status);

    Movie findBySlug(String slug);

    @Query(value = "SELECT m FROM Movie m WHERE m.status = true ORDER BY m.rating DESC")
    List<Movie> findMoviesByRating(Pageable pageable);

    @Query(value = "SELECT m FROM Movie m WHERE m.status = true AND m.type = :type ORDER BY m.rating DESC")
    List<Movie> findRelatedMovie(MovieType type, Pageable pageable);


    Page<Movie> findAllByTypeAndStatus(MovieType type, Boolean status ,Pageable pageable);

    Optional<Movie> findByIdAndStatusTrue(Integer id);


}
