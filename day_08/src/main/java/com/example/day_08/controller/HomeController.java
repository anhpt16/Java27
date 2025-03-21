package com.example.day_08.controller;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final MovieService movieService;

    @GetMapping("/")
    public String home(
        Model model
    ) {
        // Get Hot Movie
        List<ShortMovieResponse> hotMovies = movieService.getHotMovies(4);
        model.addAttribute("hotMovies", hotMovies);
        return "home";
    }

    @GetMapping("/phim-chieu-rap")
    public String movieCinema(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "18") int size,
        Model model
    ) {
        // Get list phim_chieu_rap
        Page<ShortMovieResponse> cinemaMovies = movieService.getMoviesByType(MovieType.PHIM_CHIEU_RAP, page, size);
        model.addAttribute("cinemaMovies", cinemaMovies);
        return "movies_cinema";
    }

    @GetMapping("/phim-bo")
    public String movieSeries(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "18") int size,
        Model model
    ) {
        // Get list phim_chieu_rap
        Page<ShortMovieResponse> seriesMovies = movieService.getMoviesByType(MovieType.PHIM_BO, page, size);
        model.addAttribute("seriesMovies", seriesMovies);
        return "movies_series";
    }

    @GetMapping("/phim-le")
    public String movieSingle(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "18") int size,
        Model model
    ) {
        // Get list phim_chieu_rap
        Page<ShortMovieResponse> singleMovies = movieService.getMoviesByType(MovieType.PHIM_LE, page, size);
        model.addAttribute("singleMovies", singleMovies);
        return "movies_single";
    }

    @GetMapping("/cua-hang")
    public String movieStore() {

        return "movies_store";
    }
}
