package com.example.day_08.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class MovieFile {
    private String name;
    private String thumbnail;
    private String trailer;
    private Integer duration;
    private Integer rating;
    private List<String> genres;
    private List<String> actors;
    private Integer releaseYear;
    private String director;
    private String country;
    private String description;
}
