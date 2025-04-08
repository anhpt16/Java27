package com.example.day_08.model.response;

import com.example.day_08.model.enums.MovieType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShortMovieResponse {
    private final Integer id;
    private final String name;
    private final String slug;
    private final String type;
    private final String thumbnail;
}
