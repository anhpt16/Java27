package com.example.day_08.service.impl;

import com.example.day_08.entity.User;
import com.example.day_08.model.request.CreateUserRequest;
import com.example.day_08.repository.UserRepository;
import com.example.day_08.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public User createUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setDisplayName(request.getDisplayName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user: " + id));
        return user;
    }
}
