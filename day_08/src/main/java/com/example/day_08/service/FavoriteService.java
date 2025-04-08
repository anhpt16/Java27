package com.example.day_08.service;

import com.example.day_08.entity.Favorite;
import com.example.day_08.entity.Movie;
import com.example.day_08.entity.User;
import com.example.day_08.model.response.ShortMovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoriteService {
    Page<ShortMovieResponse> getFavoriteMoviesByUser(Integer userId, int page, int size);
    Favorite addFavoriteMovieByUser(Integer userId, Integer movieId);
    Boolean deleteFavoriteMovieByUser(Integer userId, Integer movieId);
    Boolean deleteAllFavoriteMovieByUser(Integer userId);
    Boolean existFavoriteMovieByUser(Integer userId, Integer movieId);
}
