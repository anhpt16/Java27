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
    // 🔹 1️⃣ Tìm kiếm phim theo tên (case-insensitive)
    List<Movie> findByNameContainingIgnoreCase(String name);

    // 🔹 2️⃣ Tìm kiếm phim theo slug
    @Query("SELECT m FROM Movie m WHERE m.slug = :slug AND (:status IS NULL OR m.status = :status)")
    Optional<Movie> findBySlug(@Param("slug") String slug, @Param("status") Boolean status);

    // 🔹 3️⃣ Lọc phim theo năm phát hành
    List<Movie> findByReleaseYear(Integer releaseYear);

    // 🔹 4️⃣ Lọc phim theo trạng thái (còn chiếu hay không)
    List<Movie> findByStatus(Boolean status);

    // 🔹 5️⃣ Lọc phim theo loại (MovieType)
    List<Movie> findByType(MovieType type);

    // 🔹 6️⃣ Lấy danh sách phim có rating từ một giá trị trở lên
    List<Movie> findByRatingGreaterThanEqual(Double rating);

    // 🔹 7️⃣ Lọc phim theo ngày phát hành (publishedAt)
    List<Movie> findByPublishedAtAfter(LocalDateTime publishedAt);

    // 🔹 8️⃣ Lấy danh sách phim theo quốc gia
    List<Movie> findByCountryId(Integer countryId);

    // 🔹 9️⃣ Custom Query: Lấy danh sách phim có rating cao nhất
    @Query("SELECT m FROM Movie m ORDER BY m.rating DESC")
    List<Movie> findTopRatedMovies();

    // 🔹 🔟 Custom Query: Tìm kiếm phim theo từ khóa trong tên hoặc mô tả
    @Query("SELECT m FROM Movie m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Movie> searchByKeyword(@Param("keyword") String keyword);

    // 🔹 11️⃣ Custom Query: Lấy danh sách phim phát hành trong khoảng thời gian
    @Query("SELECT m FROM Movie m WHERE m.publishedAt BETWEEN :startDate AND :endDate")
    List<Movie> findMoviesBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT m FROM Movie m WHERE m.status = true ORDER BY m.rating DESC")
    List<Movie> findMoviesByRating(Pageable pageable);

    @Query(value = "SELECT m FROM Movie m WHERE m.status = true AND m.type = :type ORDER BY m.rating DESC")
    List<Movie> findRelatedMovie(MovieType type, Pageable pageable);


    Page<Movie> findAllByTypeAndStatus(MovieType type, Boolean status ,Pageable pageable);
}
