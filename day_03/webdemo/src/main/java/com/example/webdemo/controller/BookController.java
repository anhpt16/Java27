package com.example.webdemo.controller;


import com.example.webdemo.model.Book;
import com.example.webdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
- Controller: trả về template
- RestCOntroller: trả về dữ liệu dạng JSON: Controller + ResponseBody
- ResponseBody: chỉ trả về dữ liệu không trả về template
- ResponseEntity<?>: class đại diện cho một đối tượng response

* */

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    // Lấy danh sách tất cả cuốn sách
//    @GetMapping("/books")
//    @ResponseBody
//    public List<Book> book() {
//        return books;
//    }

    //Sử dụng response entity
    @GetMapping("/books")
    @ResponseBody
    public ResponseEntity<List<Book>> getAllBook() {
        return new ResponseEntity<>(bookService.findAllBook(), HttpStatus.CREATED);
    }

    // Lấy sách theo id
//    @GetMapping("/books/{id}")
//    @ResponseBody
//    public Book bookById(
//        @PathVariable int id
//    ) {
//        return books.stream()
//            .filter(book -> book.getId() == id)
//            .findFirst()
//            .orElse(null);
//    }

    // ResponseEntity
    @GetMapping("/books/{id}")
    @ResponseBody
    public ResponseEntity<Book> bookById(
        @PathVariable int id
    ) {
        Book book = bookService.findBookById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // Sắp xếp theo năm giảm dần
    @GetMapping("/books/sortByYear")
    public ResponseEntity<List<Book>> sortByYearDesc() {
        List<Book> books = bookService.findAllBook();
        books = books.stream()
            .sorted(Comparator.comparing(Book::getYear))
            .collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }

    // Tìm kiếm sách theo tên
    @GetMapping("/books/searchByTitle")
    public ResponseEntity<List<Book>> searchByTitle(
        @RequestParam String keyword
    ) {
        return new ResponseEntity<>(bookService.findBookByTitle(keyword), HttpStatus.CREATED);
    }
    // Tìm kiếm sách xuất bản trong khoảng thời gian
    @GetMapping("/books/searchByYear")
    public ResponseEntity<List<Book>> getBetween(
        @RequestParam int start,
        @RequestParam int end
    ) {
        List<Book> books = bookService.findAllBook();

        List<Book> bookFilter = books.stream()
            .filter(book -> (book.getYear() >= start && book.getYear() <= end))
            .collect(Collectors.toList());

        return new ResponseEntity<>(bookFilter, HttpStatus.CREATED);
    }
}
