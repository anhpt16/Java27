package com.example.day_06_test.dao.impl;

import com.example.day_06_test.dao.ProductDAO;
import com.example.day_06_test.database.ProductDB;
import com.example.day_06_test.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductDAOImpl implements ProductDAO {
    @Override
    public List<Product> getProductPagination(int page, int size) {
        // Checke DB
        if (ProductDB.products == null || ProductDB.products.isEmpty()) {
            return Collections.emptyList();
        }
        // Get Data
        int start = (page - 1) * size;
        return ProductDB.products.stream()
            .skip(start)
            .limit(size)
            .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductPagination(String keyword, int page, int size) {
        // Checke DB
        if (ProductDB.products == null || ProductDB.products.isEmpty()) {
            return Collections.emptyList();
        }
        // Get Data
        int start = (page - 1) * size;
        return ProductDB.products.stream()
            .filter(product -> product.getName().contains(keyword))
            .skip(start)
            .limit(size)
            .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        if (ProductDB.products == null || ProductDB.products.isEmpty()) {
            return Collections.emptyList();
        }
        return ProductDB.products.stream()
            .filter(product -> product.getName().contains(keyword))
            .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(int id) {
        if (ProductDB.products == null || ProductDB.products.isEmpty()) {
            return null;
        }
        return ProductDB.products.stream()
            .filter(product -> product.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Product> getProductRelated(String keyword, int size, int id) {
        if (ProductDB.products == null || ProductDB.products.isEmpty()) {
            return Collections.emptyList();
        }
        return ProductDB.products.stream()
            .filter(product -> product.getName().startsWith(keyword) && product.getId() != id)
            .limit(size)
            .collect(Collectors.toList());
    }
}
