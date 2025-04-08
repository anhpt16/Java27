package com.example.day_08.api;

import com.example.day_08.entity.Favorite;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@AllArgsConstructor
public class FavoriteApi {
    private final FavoriteService favoriteService;

    @GetMapping
    public Page<ShortMovieResponse> getFavoriteMovies(
        @RequestParam (name = "page", defaultValue = "1") int page,
        @RequestParam (name = "size", defaultValue = "18") int size
    ) {
        Integer defaultUserId = 1;
        Page<ShortMovieResponse> favoriteMovies = favoriteService.getFavoriteMoviesByUser(defaultUserId, page - 1, size);
        return favoriteMovies;
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<?> addToFavoriteMovies(
        @PathVariable Integer movieId
    ) {
        Integer defaultUserId = 1;
        Favorite result = favoriteService.addFavoriteMovieByUser(defaultUserId, movieId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể thêm phim vào danh sách phim yêu thích");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteFavoriteMovie(
        @PathVariable Integer movieId
    ) {
        Integer defaultUserId = 1;
        Boolean result = favoriteService.deleteFavoriteMovieByUser(defaultUserId, movieId);
        if (result != null && !result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể xóa phim khỏi danh sách yêu thích");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllFavoriteMovie() {
        Integer defaultUserId = 1;
        Boolean result = favoriteService.deleteAllFavoriteMovieByUser(defaultUserId);
        if (result != null && result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể xóa tất cả phim yêu thích");
    }


}
