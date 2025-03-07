package com.example.day_06_test.service.impl;

import com.example.day_06_test.dao.ProductDAO;
import com.example.day_06_test.database.ProductDB;
import com.example.day_06_test.model.PageResponse;
import com.example.day_06_test.model.Product;
import com.example.day_06_test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;

    @Override
    public PageResponse<Product> getProduct(int page, int size) {
        // Check page
        int totalPage = (int) Math.ceil((double) ProductDB.products.size() / size);
        if (page > totalPage) {
            return null;
        }
        if (size > ProductDB.products.size()) {
            return null;
        }
        //
        List<Product> products = productDAO.getProductPagination(page, size);
        return PageResponse.<Product>builder()
            .list(products)
            .pageCurrent(page)
            .pageTotal(totalPage)
            .build();
    }

    @Override
    public PageResponse<Product> getProduct(String keyword, int page, int size) {
        List<Product> products = productDAO.getProductPagination(keyword, page, size);
        // Check page
        int totalPage = (int) Math.ceil((double) productDAO.searchProduct(keyword).size() / size);
        if (page > totalPage) {
            return null;
        }
        if (size > ProductDB.products.size()) {
            return null;
        }
        //
        return PageResponse.<Product>builder()
            .list(products)
            .pageCurrent(page)
            .pageTotal(totalPage)
            .build();
    }

    @Override
    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }

    @Override
    public List<Product> getProductRelated(String keyword, int size, int id) {
        return productDAO.getProductRelated(keyword, size, id);
    }
}
