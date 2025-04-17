package com.example.day_08.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateEpisodeRequest {
    @NotEmpty(message = "Tên tập không được để trống")
    private String name;

    @NotNull(message = "Thứ tự sắp xếp không được để trống")
    private Integer displayOrder;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;

    @NotNull(message = "Mã phim không được để trống")
    private Integer movieId;
}
