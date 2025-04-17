package com.example.day_08.service.impl;

import com.example.day_08.entity.Episode;
import com.example.day_08.entity.Movie;
import com.example.day_08.exception.BadRequestException;
import com.example.day_08.model.enums.EpisodeStatus;
import com.example.day_08.model.request.CreateEpisodeRequest;
import com.example.day_08.model.request.UpdateEpisodeRequest;
import com.example.day_08.repository.EpisodeRepository;
import com.example.day_08.repository.MovieRepository;
import com.example.day_08.service.EpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final MovieRepository movieRepository;

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
    public Episode createEpisode(CreateEpisodeRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
            .orElseThrow(() -> new BadRequestException("Không tìm thấy movieId: " + request.getMovieId()));
        Episode episode = new Episode();
        episode.setName(request.getName());
        episode.setDisplayOrder(request.getDisplayOrder());
        if (request.getStatus()) {
            episode.setStatus(EpisodeStatus.ACTIVE);
            episode.setPublishedAt(LocalDateTime.now());
        }
        else {
            episode.setStatus(EpisodeStatus.INACTIVE);
        }
        episode.setCreatedAt(LocalDateTime.now());
        episode.setUpdatedAt(LocalDateTime.now());
        episode.setMovie(movie);
        episode.setDuration(0);
        episodeRepository.save(episode);
        return episode;
    }

    @Override
    public Episode updateEpisode(Integer episodeId, UpdateEpisodeRequest request) {
        Episode episode = episodeRepository.findById(episodeId)
            .orElseThrow(() -> new BadRequestException("Không tìm thấy episodeId: " + episodeId));
        episode.setName(request.getName());
        episode.setDisplayOrder(request.getDisplayOrder());
        if (request.getStatus() == true) {
            episode.setStatus(EpisodeStatus.ACTIVE);
            if (episode.getStatus().equals(EpisodeStatus.INACTIVE) || episode.getStatus() == null) {
                episode.setPublishedAt(LocalDateTime.now());
            }
        } else {
            episode.setStatus(EpisodeStatus.INACTIVE);
            episode.setPublishedAt(null);
        }
        episode.setUpdatedAt(LocalDateTime.now());
        Episode saved = episodeRepository.save(episode);
        return saved;
    }

    @Override
    public Boolean deleteEpisode(Integer episodeId) {
        Episode episode = episodeRepository.findById(episodeId)
            .orElseThrow(() -> new BadRequestException("Không tìm thấy episodeId: " + episodeId));
        episodeRepository.delete(episode);
        return !episodeRepository.existsById(episodeId);
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

    @Override
    public Page<Episode> getEpisodesByMovieId(Integer movieId, int page, int size) {
        if (!movieRepository.existsById(movieId)) {
            throw new BadRequestException("Không tìm thấy movieId: " + movieId);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("displayOrder").ascending());
        return episodeRepository.findByMovieId(movieId, pageable);
    }
}
