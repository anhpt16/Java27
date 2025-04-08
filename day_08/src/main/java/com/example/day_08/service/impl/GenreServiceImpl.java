package com.example.day_08.service.impl;

import com.example.day_08.entity.Genre;
import com.example.day_08.entity.Movie;
import com.example.day_08.model.request.MovieFile;
import com.example.day_08.repository.GenreRepository;
import com.example.day_08.service.GenreService;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Genre findGenreExist(String slug) {
        return genreRepository.findBySlug(slug);
    }

    @Override
    public Genre createGenreByName(String name) {
        Genre genre = new Genre();
        Slugify slugify = Slugify.builder().build();
        genre.setName(name);
        genre.setSlug(slugify.slugify(name));
        genre.setCreatedAt(LocalDateTime.now());
        genre.setUpdatedAt(LocalDateTime.now());
        return genreRepository.save(genre);
    }
}
