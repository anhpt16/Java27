package com.example.day_08.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    private final String email;
    private final String password;
}
