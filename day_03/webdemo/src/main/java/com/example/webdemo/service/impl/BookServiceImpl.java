package com.example.webdemo.service.impl;

import com.example.webdemo.dao.BookDAO;
import com.example.webdemo.model.Book;
import com.example.webdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDAO bookDAO;
    @Override
    public Book findBookById(int id) {
        return bookDAO.findById(id);
    }

    @Override
    public List<Book> findAllBook() {
        return bookDAO.finnAll();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return bookDAO.findByTitle(title);
    }

    @Override
    public List<Book> findBookByYear(int start, int end) {
        return bookDAO.findByYear(start, end);
    }
}
