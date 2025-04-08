package com.example.day_08.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MovieDetailResponse {
    private final Integer id;
    private final String type;
    private final String typeUri;
    private final String name;
    private final String slug;
    private final Integer releaseYear;
    private final String description;
    private final String thumbnail;
    private final String trailer;
    private final Integer rating;

    private final List<GenreShortResponse> genres;
    private final List<ActorShortResponse> actors;
    private final List<DirectorShortResponse> directors;
    private final CountryShortResponse country;
    private final List<EpisodeShortResponse> episodes;
}
