package com.example.day_08.service;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.response.ShortMovieResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {
    List<ShortMovieResponse> getHotMovies(int size);
    Page<ShortMovieResponse> getMoviesByType(MovieType type, int page, int size);
}
