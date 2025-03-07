package com.example.day_06_test.service;

import com.example.day_06_test.model.PageResponse;
import com.example.day_06_test.model.Product;

import java.util.List;

public interface ProductService {
    PageResponse<Product> getProduct(int page, int size);
    PageResponse<Product> getProduct(String keyword, int page, int size);
    Product getProductById(int id);
    List<Product> getProductRelated(String keyword, int size, int id);
}
