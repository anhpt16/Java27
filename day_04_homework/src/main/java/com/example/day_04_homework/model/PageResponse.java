package com.example.day_04_homework.model;

import java.util.List;

public class PageResponse<T> {
    List<T> list;
    int totalPage;
    int currentPage;
}
