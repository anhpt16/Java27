package com.example.webdemo.dao.impl;

import com.example.webdemo.dao.BookDAO;
import com.example.webdemo.database.BookDB;
import com.example.webdemo.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookDAOImpl implements BookDAO {
    @Override
    public Book findById(int id) {
        return BookDB.books.stream()
            .filter(book -> book.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Book> finnAll() {
        return BookDB.books;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return BookDB.books.stream()
            .filter(book -> book.getTitle().contains(title))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByYear(int start, int end) {
        return BookDB.books.stream()
            .filter(book -> (book.getYear() >= start && book.getYear() <= end))
            .collect(Collectors.toList());
    }

}
