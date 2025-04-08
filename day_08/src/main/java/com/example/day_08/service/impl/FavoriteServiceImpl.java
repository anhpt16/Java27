package com.example.day_08.service.impl;

import com.example.day_08.entity.Favorite;
import com.example.day_08.entity.Movie;
import com.example.day_08.entity.User;
import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.repository.FavoriteRepository;
import com.example.day_08.repository.MovieRepository;
import com.example.day_08.repository.UserRepository;
import com.example.day_08.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public Page<ShortMovieResponse> getFavoriteMoviesByUser(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> results = favoriteRepository.findFavoriteMovieByUser(userId, true, pageable);
        if (results.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }
        List<ShortMovieResponse> movieResponses =  results.stream()
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
    public Favorite addFavoriteMovieByUser(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy UserId: " + userId));
        Movie movie = movieRepository.findByIdAndStatusTrue(movieId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy MovieId: " + movieId));
        Favorite favorite = favoriteRepository.findByUserIdAndMovieId(userId, movieId);
        if (favorite != null) {
            throw new RuntimeException("Người dùng đã thích bộ phim này");
        }
        Favorite result = new Favorite();
        result.setUser(user);
        result.setMovie(movie);
        result.setCreatedAt(LocalDateTime.now());
        return favoriteRepository.save(result);
    }

    @Override
    public Boolean deleteFavoriteMovieByUser(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy UserId: " + userId));
        Movie movie = movieRepository.findByIdAndStatusTrue(movieId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy MovieId: " + movieId));
        Favorite favorite = favoriteRepository.findByUserIdAndMovieId(userId, movieId);
        if (favorite == null) {
            throw new RuntimeException("Người dùng chưa thích bộ phim này");
        }
        favoriteRepository.delete(favorite);
        return favoriteRepository.existsById(favorite.getId());
    }

    @Transactional
    @Override
    public Boolean deleteAllFavoriteMovieByUser(Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy UserId: " + userId));
        List<Favorite> favorites = user.getFavorites();
        if (favorites.isEmpty()) {
            return true;
        }
        favoriteRepository.deleteByUserId(userId);
        return true;
    }

    @Override
    public Boolean existFavoriteMovieByUser(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy UserId: " + userId));
        Movie movie = movieRepository.findByIdAndStatusTrue(movieId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy MovieId: " + movieId));
        return favoriteRepository.existsByUserIdAndMovieId(userId, movieId);
    }
}
