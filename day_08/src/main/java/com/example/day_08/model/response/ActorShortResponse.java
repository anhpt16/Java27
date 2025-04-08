package com.example.day_08.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActorShortResponse {
    private String name;
    private String slug;
}
