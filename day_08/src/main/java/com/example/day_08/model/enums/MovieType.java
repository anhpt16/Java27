package com.example.day_08.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum MovieType {
    PHIM_BO("Phim bộ", "/phim-bo"),
    PHIM_LE("Phim lẻ", "/phim-le"),
    PHIM_CHIEU_RAP("Phim chiếu rạp", "/phim-chieu-rap");

    private static final Map<String, MovieType> VALUE_MAP =
        Stream.of(MovieType.values()).collect(Collectors.toMap(MovieType::getValue, e -> e));


    private final String value;
    private final String uri;

    public static MovieType fromValue(String value) {
        return VALUE_MAP.getOrDefault(value, null);
    }
}
