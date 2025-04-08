package com.example.day_08.service.impl;

import com.example.day_08.entity.Director;
import com.example.day_08.repository.DirectorRepository;
import com.example.day_08.service.DirectorService;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;

    @Override
    public Director findDirectorExist(String slug) {
        return directorRepository.findBySlug(slug);
    }

    @Override
    public Director createDirectorByName(String name) {
        Director director = new Director();
        Slugify slugify = Slugify.builder().build();
        director.setName(name);
        director.setSlug(slugify.slugify(name));
        director.setCreatedAt(LocalDateTime.now());
        director.setUpdatedAt(LocalDateTime.now());
        return directorRepository.save(director);
    }
}
