package com.example.day_08.service;

import com.example.day_08.entity.Episode;
import com.example.day_08.entity.Movie;
import com.example.day_08.model.request.CreateEpisodeRequest;
import com.example.day_08.model.request.UpdateEpisodeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EpisodeService {
    Episode createEpisode(Integer duration, Movie movie);
    Episode createEpisode(CreateEpisodeRequest request);
    Episode updateEpisode(Integer episodeId, UpdateEpisodeRequest request);
    Boolean deleteEpisode(Integer episodeId);
    void createEpisodes(Integer duration, Movie movie);
    Page<Episode> getEpisodesByMovieId(Integer movieId, int page, int size);
}
