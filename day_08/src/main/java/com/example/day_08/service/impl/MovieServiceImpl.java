package com.example.day_08.service.impl;

import com.example.day_08.entity.Country;
import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.response.*;
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
    public Movie findMovieExist(String slug) {
        return movieRepository.findBySlug(slug);
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<ShortMovieResponse> getHotMovies(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("rating").descending());
        List<Movie> results = movieRepository.findMoviesByRating(pageable);
        if (results.isEmpty()) {
            return Collections.emptyList();
        }
        return results.stream()
            .map(movie -> ShortMovieResponse.builder()
                .id(movie.getId())
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
                .id(movie.getId())
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
                .id(movie.getId())
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
        List<GenreShortResponse> genres = Optional.ofNullable(movie.getGenres())
            .orElse(Collections.emptyList())
            .stream()
            .map(g -> GenreShortResponse.builder()
                .name(g.getName())
                .slug(g.getSlug())
                .build())
            .collect(Collectors.toList());
        List<ActorShortResponse> actors = Optional.ofNullable(movie.getActors())
            .orElse(Collections.emptyList())
            .stream()
            .map(a -> ActorShortResponse.builder()
                .name(a.getName())
                .slug(a.getSlug())
                .build())
            .collect(Collectors.toList());
        List<DirectorShortResponse> director = Optional.ofNullable(movie.getDirectors())
            .orElse(Collections.emptyList())
            .stream()
            .map(d -> DirectorShortResponse.builder()
                .name(d.getName())
                .slug(d.getSlug())
                .build())
            .collect(Collectors.toList());
        CountryShortResponse countryShortResponse = null;
        if (movie.getCountry() != null) {
            countryShortResponse = CountryShortResponse.builder()
                .name(movie.getCountry().getName())
                .slug(movie.getCountry().getSlug())
                .build();
        }
        List<EpisodeShortResponse> episodes = Optional.ofNullable(movie.getEpisodes())
            .orElse(Collections.emptyList())
            .stream()
            .map(e -> EpisodeShortResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .displayOrder(e.getDisplayOrder())
                .build())
            .collect(Collectors.toList());
        return MovieDetailResponse.builder()
            .id(movie.getId())
            .type(movie.getType().getValue())
            .typeUri(movie.getType().getUri())
            .name(movie.getName())
            .slug(movie.getSlug())
            .releaseYear(movie.getReleaseYear())
            .description(movie.getDescription())
            .thumbnail(movie.getThumbnail())
            .trailer(movie.getTrailer())
            .rating(movie.getRating())
            .genres(genres)
            .actors(actors)
            .directors(director)
            .country(countryShortResponse)
            .episodes(episodes)
            .build();
    }
}
