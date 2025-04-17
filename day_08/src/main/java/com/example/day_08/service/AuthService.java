package com.example.day_08.service;

import com.example.day_08.model.request.LoginRequest;

public interface AuthService {
    void login(LoginRequest request);

    void logout();
}
