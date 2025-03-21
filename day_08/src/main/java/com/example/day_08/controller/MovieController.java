package com.example.day_08.controller;

import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.response.MovieDetailResponse;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.service.MovieService;
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

    @GetMapping("/{slug}")
    public String movie(
        @PathVariable String slug,
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
        return "movie_detail";
    }
}
