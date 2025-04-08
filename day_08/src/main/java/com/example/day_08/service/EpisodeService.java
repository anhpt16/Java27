package com.example.day_08.service;

import com.example.day_08.entity.Episode;
import com.example.day_08.entity.Movie;

public interface EpisodeService {
    Episode createEpisode(Integer duration, Movie movie);
    void createEpisodes(Integer duration, Movie movie);
}
