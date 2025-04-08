package com.example.day_08.model.request;

import com.example.day_08.model.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserRequest {
    private String username;
    private String password;
    private UserRole role;
    private String displayName;
    private String email;
    private String phone;
}
