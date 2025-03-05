package com.example.day_04_homework.util;

import com.example.day_04_homework.model.People;

import java.util.List;

public interface IFileReader {
    List<People> readFile(String path);
}
