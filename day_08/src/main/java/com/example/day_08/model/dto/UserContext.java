package com.example.day_08.model.dto;

public class UserContext {
    private static final ThreadLocal<UserDTO> currentUser = new ThreadLocal<>();

    public static void setUser(UserDTO user) {
        currentUser.set(user);
    }

    public static UserDTO getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
