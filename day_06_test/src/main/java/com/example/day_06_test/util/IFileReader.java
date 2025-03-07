package com.example.day_06_test.util;

import com.example.day_06_test.model.Product;

import java.util.List;

public interface IFileReader {
    List<Product> readFile(String path);
}
