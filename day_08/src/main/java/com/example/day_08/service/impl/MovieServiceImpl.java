package com.example.day_08.service.impl;

import com.example.day_08.entity.*;
import com.example.day_08.exception.BadRequestException;
import com.example.day_08.model.enums.MovieType;
import com.example.day_08.model.request.CreateMovieRequest;
import com.example.day_08.model.response.*;
import com.example.day_08.repository.*;
import com.example.day_08.service.MovieService;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;


    @Override
    public Movie findMovieExist(String slug) {
        return movieRepository.findBySlug(slug);
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie createMovie(CreateMovieRequest request) {
        Movie movie = new Movie();
        movie.setName(request.getName());

        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(request.getName());
        movie.setSlug(slug);

        // Lấy danh sách diễn viên
        if (!request.getActorIds().isEmpty()) {
            List<Actor> actors = new ArrayList<>();
            for (Integer id : request.getActorIds()) {
                Optional<Actor> actor = actorRepository.findById(id);
                actor.ifPresent(actors::add);
            }
            movie.setActors(actors);
        }
        // Lấy danh sách đạo diễn
        if (!request.getDirectorIds().isEmpty()) {
            List<Director> directors = new ArrayList<>();
            for(Integer id : request.getDirectorIds()) {
                Optional<Director> director = directorRepository.findById(id);
                director.ifPresent(directors::add);
            }
            movie.setDirectors(directors);
        }
        // Lấy danh sách thể loại
        if (!request.getGenreIds().isEmpty()) {
            List<Genre> genres = new ArrayList<>();
            for(Integer id : request.getGenreIds()) {
                Optional<Genre> genre = genreRepository.findById(id);
                genre.ifPresent(genres::add);
            }
            movie.setGenres(genres);
        }
        // Lấy quốc gia
        Optional<Country> country = countryRepository.findById(request.getCountryId());
        country.ifPresent(movie::setCountry);

        if (request.getStatus()) {
            movie.setPublishedAt(LocalDateTime.now());
        }


        movie.setTrailer(request.getTrailerUrl());
        movie.setDescription(request.getDescription());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setType(request.getType());
        movie.setStatus(request.getStatus());
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        // Thêm mới movie
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(Integer movieId, CreateMovieRequest request) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestException("Không tìm thấy movieId: " + movieId));

        movie.setName(request.getName());
        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(request.getName());
        movie.setSlug(slug);

        // Lấy danh sách diễn viên
        if (!request.getActorIds().isEmpty()) {
            List<Actor> actors = new ArrayList<>();
            for (Integer id : request.getActorIds()) {
                Optional<Actor> actor = actorRepository.findById(id);
                actor.ifPresent(actors::add);
            }
            movie.setActors(actors);
        }
        // Lấy danh sách đạo diễn
        if (!request.getDirectorIds().isEmpty()) {
            List<Director> directors = new ArrayList<>();
            for(Integer id : request.getDirectorIds()) {
                Optional<Director> director = directorRepository.findById(id);
                director.ifPresent(directors::add);
            }
            movie.setDirectors(directors);
        }
        // Lấy danh sách thể loại
        if (!request.getGenreIds().isEmpty()) {
            List<Genre> genres = new ArrayList<>();
            for(Integer id : request.getGenreIds()) {
                Optional<Genre> genre = genreRepository.findById(id);
                genre.ifPresent(genres::add);
            }
            movie.setGenres(genres);
        }
        // Lấy quốc gia
        Optional<Country> country = countryRepository.findById(request.getCountryId());
        country.ifPresent(movie::setCountry);

        if (request.getStatus()) {
            movie.setPublishedAt(LocalDateTime.now());
        }


        movie.setTrailer(request.getTrailerUrl());
        movie.setDescription(request.getDescription());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setType(request.getType());
        movie.setStatus(request.getStatus());
        movie.setUpdatedAt(LocalDateTime.now());
        if (!movie.getStatus()) {
            movie.setPublishedAt(null);
        }
        Movie saved = movieRepository.save(movie);
        return saved;
    }

    @Override
    public Boolean deleteMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestException("Không tìm thấy movieId:" + movieId));
        movieRepository.delete(movie);
        if (movieRepository.findById(movieId).isPresent()) {
            return false;
        }
        return true;
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
    public Page<ShortMovieResponse> getMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> results = movieRepository.findAll(pageable);
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
