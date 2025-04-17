package com.example.day_08.api.admin;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.request.CreateMovieRequest;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class MovieApi {
    private final MovieService movieService;

    @GetMapping
    public Page<ShortMovieResponse> getMovies(
        @RequestParam (name = "page", defaultValue = "1") int page,
        @RequestParam (name = "size", defaultValue = "10") int size
    ) {
        return movieService.getMovies(page - 1, size);
    }

    @PostMapping
    public ResponseEntity<?> createNewMovie(
        @RequestBody @Valid CreateMovieRequest request
    ) {
        Movie movie = movieService.createMovie(request);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<?> updateMovie(
        @PathVariable Integer movieId,
        @RequestBody @Valid CreateMovieRequest request
    ) {
        Movie movie = movieService.updateMovie(movieId, request);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteMovie(
        @PathVariable Integer movieId
    ) {
        Boolean result = movieService.deleteMovie(movieId);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
