package com.example.day_08.util.file;

import com.example.day_08.entity.Movie;
import com.example.day_08.model.request.MovieFile;

import java.util.List;

public interface IFileReader {
    List<MovieFile> readFile(String path);
}
