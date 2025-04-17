package com.example.day_08.service.impl;

import com.example.day_08.entity.User;
import com.example.day_08.exception.BadRequestException;
import com.example.day_08.model.dto.UserDTO;
import com.example.day_08.model.request.LoginRequest;
import com.example.day_08.repository.UserRepository;
import com.example.day_08.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final HttpSession session;

    public void login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadRequestException("Tài khoản hoặc mật khẩu không chính xác"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Tài khoản hoặc mật khẩu không chính xác");
        }

        // Luu lai: session, cookie, database, redis, ...
        UserDTO currentUser = UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .displayName(user.getDisplayName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole())
            .build();
        session.setAttribute("currentUser", currentUser);
    }

    public void logout() {
        session.removeAttribute("currentUser");
    }
}
