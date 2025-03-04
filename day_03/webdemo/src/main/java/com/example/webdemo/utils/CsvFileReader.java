package com.example.webdemo.utils;

import com.example.webdemo.model.Book;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileReader implements IFileReader{
    @Override
    public List<Book> readFile(String path) {
        List<Book> books = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] line;
            reader.readNext(); // Bỏ qua dòng tiêu đề (nếu có)
            while ((line = reader.readNext()) != null) {
                int id = Integer.parseInt(line[0]);
                String title = line[1];
                String author = line[2];
                int year = Integer.parseInt(line[3]);

                Book book = new Book(id, title, author, year);
                books.add(book);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return books;
    }
}
