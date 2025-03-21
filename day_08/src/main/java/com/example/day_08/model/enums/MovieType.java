package com.example.day_08.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MovieType {
    PHIM_BO("Phim bộ"),
    PHIM_LE("Phim lẻ"),
    PHIM_CHIEU_RAP("Phim chiếu rạp");

    private final String value;
}
