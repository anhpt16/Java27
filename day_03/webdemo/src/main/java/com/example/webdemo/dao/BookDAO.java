package com.example.webdemo.dao;

import com.example.webdemo.model.Book;

import java.util.List;

public interface BookDAO {
    Book findById(int id);
    List<Book> finnAll();
    List<Book> findByTitle(String title);
    List<Book> findByYear(int start, int end);
}
