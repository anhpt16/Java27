package com.example.day_08.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateReviewRequest {
    private Integer movieId;
    private String content;
    private Integer rating;
}
