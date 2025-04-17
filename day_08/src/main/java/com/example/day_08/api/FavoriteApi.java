package com.example.day_08.api;

import com.example.day_08.entity.Favorite;
import com.example.day_08.model.dto.UserContext;
import com.example.day_08.model.dto.UserDTO;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
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
    private final HttpSession session;

    @GetMapping
    public Page<ShortMovieResponse> getFavoriteMovies(
        @RequestParam (name = "page", defaultValue = "1") int page,
        @RequestParam (name = "size", defaultValue = "18") int size
    ) {
        Integer userId = UserContext.getUser().getId();
        Page<ShortMovieResponse> favoriteMovies = favoriteService.getFavoriteMoviesByUser(userId, page - 1, size);
        return favoriteMovies;
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<?> addToFavoriteMovies(
        @PathVariable Integer movieId
    ) {
        Integer userId = UserContext.getUser().getId();
        Favorite result = favoriteService.addFavoriteMovieByUser(userId, movieId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể thêm phim vào danh sách phim yêu thích");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteFavoriteMovie(
        @PathVariable Integer movieId
    ) {
        Integer userId = UserContext.getUser().getId();
        Boolean result = favoriteService.deleteFavoriteMovieByUser(userId, movieId);
        if (result != null && !result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể xóa phim khỏi danh sách yêu thích");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllFavoriteMovie() {
        Integer userId = UserContext.getUser().getId();
        Boolean result = favoriteService.deleteAllFavoriteMovieByUser(userId);
        if (result != null && result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể xóa tất cả phim yêu thích");
    }


}
