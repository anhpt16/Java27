package com.example.webdemo.utils;

import com.example.webdemo.model.Book;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class excelFileReader implements IFileReader{
    @Override
    public List<Book> readFile(String path) {
        List<Book> books = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Đọc sheet đầu tiên
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Bỏ qua dòng tiêu đề (nếu có)
                    continue;
                }
                int id = (int) row.getCell(0).getNumericCellValue();
                String title = row.getCell(1).getStringCellValue();
                String author = row.getCell(2).getStringCellValue();
                int year = (int) row.getCell(3).getNumericCellValue();

                Book book = new Book(id, title, author, year);
                books.add(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
}
