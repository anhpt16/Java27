package com.example.day_08.service.impl;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.response.MovieDetailResponse;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.repository.MovieRepository;
import com.example.day_08.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<ShortMovieResponse> getHotMovies(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("rating").descending());
        List<Movie> results = movieRepository.findMoviesByRating(pageable);
        if (results.isEmpty()) {
            return Collections.emptyList();
        }
        return results.stream()
            .map(movie -> ShortMovieResponse.builder()
                .name(movie.getName())
                .slug(movie.getSlug())
                .type(movie.getType().getValue())
                .thumbnail(movie.getThumbnail())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public List<ShortMovieResponse> getRelatedMovies(MovieType movieType, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("rating").descending());
        List<Movie> results = movieRepository.findRelatedMovie(movieType, pageable);
        if (results.isEmpty()) {
            return Collections.emptyList();
        }
        return results.stream()
            .map(movie -> ShortMovieResponse.builder()
                .name(movie.getName())
                .slug(movie.getSlug())
                .type(movie.getType().getValue())
                .thumbnail(movie.getThumbnail())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public Page<ShortMovieResponse> getMoviesByType(MovieType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> results = movieRepository.findAllByTypeAndStatus(type, true, pageable);
        if (results.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }
        List<ShortMovieResponse> movieResponses = results.getContent().stream()
            .map(movie -> ShortMovieResponse.builder()
                .name(movie.getName())
                .slug(movie.getSlug())
                .type(movie.getType().getValue())
                .thumbnail(movie.getThumbnail())
                .build())
            .collect(Collectors.toList());
        return new PageImpl<>(movieResponses, pageable, results.getTotalElements());
    }

    @Override
    public MovieDetailResponse getMovieDetailBySlug(String slug) {
        Optional<Movie> result = movieRepository.findBySlug(slug, true);
        if (!result.isPresent()) {
            return null;
        }
        Movie movie = result.get();
        return MovieDetailResponse.builder()
            .type(movie.getType().getValue())
            .typeUri(movie.getType().getUri())
            .name(movie.getName())
            .releaseYear(movie.getReleaseYear())
            .description(movie.getDescription())
            .thumbnail(movie.getThumbnail())
            .trailer(movie.getTrailer())
            .rating(movie.getRating())
            .build();
    }
}
