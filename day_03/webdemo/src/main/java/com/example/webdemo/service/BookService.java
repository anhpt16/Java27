package com.example.webdemo.service;

import com.example.webdemo.model.Book;

import java.util.List;

public interface BookService {
    Book findBookById(int id);
    List<Book> findAllBook();
    List<Book> findBookByTitle(String title);
    List<Book> findBookByYear(int start, int end);
}
