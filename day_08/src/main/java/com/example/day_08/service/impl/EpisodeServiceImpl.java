package com.example.day_08.service.impl;

import com.example.day_08.entity.Episode;
import com.example.day_08.entity.Movie;
import com.example.day_08.model.enums.EpisodeStatus;
import com.example.day_08.repository.EpisodeRepository;
import com.example.day_08.service.EpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {
    private final EpisodeRepository episodeRepository;

    @Override
    public Episode createEpisode(Integer duration, Movie movie) {
        Episode episode = new Episode();
        episode.setName("Tập Full");
        episode.setDuration(duration);
        episode.setStatus(EpisodeStatus.ACTIVE);
        episode.setPublishedAt(LocalDateTime.now());
        episode.setCreatedAt(LocalDateTime.now());
        episode.setUpdatedAt(LocalDateTime.now());
        episode.setMovie(movie);
        return episodeRepository.save(episode);
    }

    @Override
    public void createEpisodes(Integer duration, Movie movie) {
        if (duration <= 0) {
            return;
        }
        for (int i = 1; i <= duration; i++) {
            Episode episode = new Episode();
            episode.setName("Tập " + i);
            episode.setDuration(0);
            episode.setDisplayOrder(i);
            episode.setStatus(EpisodeStatus.ACTIVE);
            episode.setCreatedAt(LocalDateTime.now());
            episode.setUpdatedAt(LocalDateTime.now());
            episode.setPublishedAt(LocalDateTime.now());
            episode.setMovie(movie);
            episodeRepository.save(episode);
        }
    }
}
