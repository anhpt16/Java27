package com.example.day_01.controller;

import com.example.day_01.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    public final List<Product> listProduct = new ArrayList<>(List.of(
            new Product(1, "Iphone 16 Promax", "Mô tả cho Iphone 16 promax", 1200, "Điện thoại"),
            new Product(2, "Tai nghe Xiaomi M242", "Mô tả cho tai nghe Xiaomi M242", 200, "Tai nghe"),
            new Product(3, "Tai nghe Samsung Galaxy Buds3 Pro R630N", "Mô tả cho tai nghe Samsung Galaxy Buds3 Pro R630N", 500, "Tai nghe"),
            new Product(4, "Điện thoại OPPO Reno13 5G 12GB/256GB", "Mô tả cho điện thoại OPPO Reno13 5G 12GB/256GB", 1220, "Điện thoại"),
            new Product(5, "Laptop Asus Vivobook Go 15 E1504FA R5 7520U/16GB/512GB/Chuột/Win11 (NJ776W)", "Mô tả cho Laptop Asus Vivobook Go 15 E1504FA R5 7520U/16GB/512GB/Chuột/Win11 (NJ776W)", 3200, "Laptop"),
            new Product(6, "Laptop Apple MacBook Air 13 inch M1 8GB/256GB", "Mô tả cho laptop Apple MacBook Air 13 inch M1 8GB/256GB", 2500, "Laptop")
    ));

    @GetMapping("/get-all")
    public List<Product> getAllProducts() {
        return Optional.ofNullable(listProduct)
                .filter(lst -> !lst.isEmpty())
                .orElse(Collections.emptyList());
    }

    @GetMapping("/{id}")
    public Product getProductById(
        @PathVariable Integer id
    ) {
        return listProduct.stream()
                .filter(pro -> pro.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
