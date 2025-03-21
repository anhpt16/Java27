package com.example.day_08.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieDetailResponse {
    private final String type;
    private final String typeUri;
    private final String name;
    private final Integer releaseYear;
    private final String description;
    private final String thumbnail;
    private final String trailer;
    private final Double rating;

}
