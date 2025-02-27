package com.example.day_03.controller;

import com.example.day_03.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    public final List<Product> listProduct = new ArrayList<>(List.of(
        new Product(1, "Laptop Apple MacBook Air 13 inch M1 8GB/256GB", "Mô tả cho laptop Apple MacBook Air 13 inch M1", 2300, "Laptop"),
        new Product(2, "Laptop Acer Nitro V 15 ANV15 41 R2UP R5", "Mô tả cho laptop Acer Nitro V 15 ANV15 41 R2UP R5", 2400, "Laptop"),
        new Product(3, "Tai nghe TWS Samsung Galaxy Buds3 Pro R630N", "Mô tả cho tai nghe TWS Samsung Galaxy Buds3 Pro R630N", 360, "Tai nghe"),
        new Product(4, "Tai nghe TWS Xiaomi Redmi Buds 6", "Mô tả cho tai nghe TWS Xiaomi Redmi Buds 6", 220, "Tai nghe"),
        new Product(5, "Điện thoại iPhone 13 512GB", "Mô tả cho điện thoại iPhone 13 512GB", 1200, "Điện thoại"),
        new Product(6, "Điện thoại Xiaomi Redmi Note 14 Pro 5G 8GB/256GB", "Mô tả cho điện thoại Xiaomi Redmi Note 14 Pro 5G 8GB/256GB", 1000, "Điện thoại")
    ));

    @GetMapping("/get-all")
    public List<Product> getALlProducts() {
        return listProduct;
    }

    @GetMapping("/{id}")
    public Product getProductById(
        @PathVariable Integer id
    ) {
        if (listProduct.isEmpty() || id <= 0) {
            return null;
        }
        return listProduct.stream()
            .filter(pro -> pro.getId() == id)
            .findFirst()
            .orElse(null);
    }
}
