package com.example.day_06_test.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {
    private List<T> list;
    private int pageTotal;
    private int pageCurrent;

}
