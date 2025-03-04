package com.example.webdemo.database;

import com.example.webdemo.model.Book;
import com.example.webdemo.utils.IFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {
    @Autowired
    @Qualifier("csvFileReader")
    private IFileReader csvFileReader;

    @Autowired
    @Qualifier("jsonFileReader")
    private IFileReader jsonFileReader;

    @Autowired
    @Qualifier("excelFileReader")
    private IFileReader excelFileReader;

    @Override
    public void run(String... args) throws Exception {
//        BookDB.books = csvFileReader.readFile("books.csv");
//        BookDB.books = jsonFileReader.readFile("books.json");
        BookDB.books = excelFileReader.readFile("books.xlsx");
        System.out.println(BookDB.books.size());
    }
}
