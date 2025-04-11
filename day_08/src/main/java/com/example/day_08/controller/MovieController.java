package com.example.day_08.controller;

import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.response.MovieDetailResponse;
import com.example.day_08.model.response.ReviewResponse;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.service.FavoriteService;
import com.example.day_08.service.MovieService;
import com.example.day_08.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/phim")
public class MovieController {

    private final MovieService movieService;
    private final FavoriteService favoriteService;
    private final ReviewService reviewService;

    @GetMapping("/{slug}")
    public String movie(
        @PathVariable String slug,
        Model model
    ) {
        Integer defaultUserId = 1;
        // Get Movie Detail
        MovieDetailResponse movieDetailResponse = movieService.getMovieDetailBySlug(slug);
        if (movieDetailResponse == null) {
            return "not_found";
        }
        // Check if favorite movie
        boolean isFavorite = favoriteService.existFavoriteMovieByUser(defaultUserId, movieDetailResponse.getId());
        // Get Movie Related
        List<ShortMovieResponse> relatedMovies = movieService.getRelatedMovies(MovieType.fromValue(movieDetailResponse.getType()), 6);
        // Get Reviews
        List<ReviewResponse> reviews = reviewService.getReviews(movieDetailResponse.getId());

        model.addAttribute("movieDetail", movieDetailResponse);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("relatedMovies", relatedMovies);
        model.addAttribute("reviews", reviews);

        return "movie_detail";
    }

    @GetMapping("/xem-phim/{slug}/{id}")
    public String watchMovie(
        @PathVariable String slug,
        @PathVariable Integer id,
        Model model
    ) {
        // Get Movie Detail
        MovieDetailResponse movieDetailResponse = movieService.getMovieDetailBySlug(slug);
        if (movieDetailResponse == null) {
            return "not_found";
        }
        // Get Movie Related
        List<ShortMovieResponse> relatedMovies = movieService.getRelatedMovies(MovieType.fromValue(movieDetailResponse.getType()), 6);
        model.addAttribute("movieDetail", movieDetailResponse);
        model.addAttribute("relatedMovies", relatedMovies);
        return "watch_movie";
    }
}
