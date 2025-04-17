package com.example.day_08.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {
    private final Integer id;
    private final Integer userId;
    private final String name;
    private final Integer rating;
    private final LocalDateTime date;
    private final String content;
}
