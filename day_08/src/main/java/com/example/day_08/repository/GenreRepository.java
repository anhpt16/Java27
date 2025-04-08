package com.example.day_08.repository;

import com.example.day_08.entity.Genre;
import com.example.day_08.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findBySlug(String slug);
}
