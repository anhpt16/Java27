package com.example.day_08.entity;

import com.example.day_08.model.enums.MovieType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
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

    @Column(columnDefinition = "double default 0")
    private Integer rating;
    private Boolean status;

    @Column(columnDefinition = "TEXT")
    private String trailer;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
        name = "movies_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
        name = "movies_directors",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors;

    @ManyToMany
    @JoinTable(
        name = "movies_actors",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @OneToMany(mappedBy = "movie")
    private List<History> histories;

    @OneToMany(mappedBy = "movie")
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "movie")
    private List<Episode> episodes;
}
