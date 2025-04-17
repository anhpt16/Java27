package com.example.day_08.model.dto;

import com.example.day_08.model.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String displayName;
    private String email;
    private String avatar;
    private String phone;
    UserRole role;
}
