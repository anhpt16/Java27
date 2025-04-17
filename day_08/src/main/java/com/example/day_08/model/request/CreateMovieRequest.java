package com.example.day_08.model.request;

import com.example.day_08.model.enums.MovieType;
import com.example.day_08.validation.ValidMovieType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateMovieRequest {
    @NotEmpty(message = "Tên không được để trống")
    private String name;

    @NotEmpty(message = "Trailer không được để trống")
    private String trailerUrl;

    @NotEmpty(message = "Mô tả không được để trống")
    private String description;


    private List<Integer> genreIds;
    private List<Integer> actorIds;
    private List<Integer> directorIds;

    @NotNull(message = "Năm sản xuất không được để trống")
    private Integer releaseYear;

    @NotNull(message = "Loại phim không được để trống")
    @ValidMovieType
    private MovieType type;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;

    @NotNull(message = "Mã quốc gia không được để trống")
    private Integer countryId;
}
