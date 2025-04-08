package com.example.day_08.service;

import com.example.day_08.entity.Genre;
import com.example.day_08.entity.Movie;
import com.example.day_08.model.request.MovieFile;

public interface GenreService {
    Genre findGenreExist(String slug);
    Genre createGenreByName(String name);
}
