package com.example.day_06_test.dao;

import com.example.day_06_test.model.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> getProductPagination(int page, int size);
    List<Product> getProductPagination(String keyword, int page, int size);
    List<Product> searchProduct(String keyword);
    Product getProductById(int id);
    List<Product> getProductRelated(String keyword, int size, int id);
}
