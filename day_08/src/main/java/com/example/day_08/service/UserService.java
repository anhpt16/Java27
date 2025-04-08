package com.example.day_08.service;

import com.example.day_08.entity.User;
import com.example.day_08.model.request.CreateUserRequest;

public interface UserService {
    User createUser(CreateUserRequest request);
    User getUserById(Integer id);
}
