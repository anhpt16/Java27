package com.example.day_08.service;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.request.MovieFile;
import com.example.day_08.model.response.MovieDetailResponse;
import com.example.day_08.model.response.ShortMovieResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {
    Movie findMovieExist(String slug);
    Movie createMovie(Movie movie);
    List<ShortMovieResponse> getHotMovies(int size);
    List<ShortMovieResponse> getRelatedMovies(MovieType type, int size);
    Page<ShortMovieResponse> getMoviesByType(MovieType type, int page, int size);
    MovieDetailResponse getMovieDetailBySlug(String slug);
}
