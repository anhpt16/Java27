package com.example.day_08.entity;

import com.example.day_08.model.enums.MovieType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Table(name = "tbl_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String thumbnail;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    private MovieType type;

    @Column(columnDefinition = "double default 5.0")
    private Double rating;
    private Boolean status;
    private String trailer;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "country_id")
    private Integer countryId;

}
