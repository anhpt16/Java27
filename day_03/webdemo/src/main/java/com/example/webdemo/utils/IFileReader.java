package com.example.webdemo.utils;

import com.example.webdemo.model.Book;

import java.util.List;

public interface IFileReader {
    List<Book> readFile(String path);
}
