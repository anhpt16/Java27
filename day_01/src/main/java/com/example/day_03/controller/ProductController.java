package com.example.day_03.controller;

import com.example.day_03.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    public final List<Product> listProduct = new ArrayList<>(List.of(
        new Product(1, "Laptop Apple MacBook Air 13 inch M1 8GB/256GB", "Mô tả cho laptop Apple MacBook Air 13 inch M1", 2300, "Apple"),
        new Product(2, "Laptop Acer Nitro V 15 ANV15 41 R2UP R5", "Mô tả cho laptop Acer Nitro V 15 ANV15 41 R2UP R5", 2400, "Acer"),
        new Product(3, "Tai nghe TWS Samsung Galaxy Buds3 Pro R630N", "Mô tả cho tai nghe TWS Samsung Galaxy Buds3 Pro R630N", 360, "Samsung"),
        new Product(4, "Tai nghe TWS Xiaomi Redmi Buds 6", "Mô tả cho tai nghe TWS Xiaomi Redmi Buds 6", 220, "Xiaomi"),
        new Product(5, "Điện thoại iPhone 13 512GB", "Mô tả cho điện thoại iPhone 13 512GB", 1200, "Apple"),
        new Product(6, "Điện thoại Xiaomi Redmi Note 14 Pro 5G 8GB/256GB", "Mô tả cho điện thoại Xiaomi Redmi Note 14 Pro 5G 8GB/256GB", 1000, "Xiaomi"),
        new Product(7, "Laptop Dell XPS 13 9310", "Mô tả cho laptop Dell XPS 13 9310", 2000, "Dell"),
        new Product(8, "Tai nghe Sony WH-1000XM4", "Mô tả cho tai nghe Sony WH-1000XM4", 350, "Sony"),
        new Product(9, "Điện thoại Samsung Galaxy S22 Ultra 512GB", "Mô tả cho điện thoại Samsung Galaxy S22 Ultra 512GB", 1300, "Samsung"),
        new Product(10, "Điện thoại Google Pixel 6 Pro", "Mô tả cho điện thoại Google Pixel 6 Pro", 1100, "Google"),
        new Product(11, "Laptop HP Spectre x360 14", "Mô tả cho laptop HP Spectre x360 14", 2200, "HP"),
        new Product(12, "Tai nghe JBL Free X", "Mô tả cho tai nghe JBL Free X", 180, "JBL"),
        new Product(13, "Điện thoại OnePlus 9 Pro", "Mô tả cho điện thoại OnePlus 9 Pro", 1200, "OnePlus"),
        new Product(14, "Laptop Lenovo ThinkPad X1 Carbon Gen 9", "Mô tả cho laptop Lenovo ThinkPad X1 Carbon Gen 9", 2500, "Lenovo"),
        new Product(15, "Tai nghe Bose QuietComfort 35 II", "Mô tả cho tai nghe Bose QuietComfort 35 II", 400, "Bose"),
        new Product(16, "Điện thoại Xiaomi Mi 11 Ultra", "Mô tả cho điện thoại Xiaomi Mi 11 Ultra", 1050, "Xiaomi"),
        new Product(17, "Máy tính bảng Apple iPad Pro 12.9 inch", "Mô tả cho máy tính bảng Apple iPad Pro 12.9 inch", 1300, "Apple"),
        new Product(18, "Máy tính bảng Samsung Galaxy Tab S7+", "Mô tả cho máy tính bảng Samsung Galaxy Tab S7+", 900, "Samsung"),
        new Product(19, "Điện thoại Vivo X70 Pro", "Mô tả cho điện thoại Vivo X70 Pro", 950, "Vivo"),
        new Product(20, "Điện thoại Oppo Reno6 Pro 5G", "Mô tả cho điện thoại Oppo Reno6 Pro 5G", 850, "Oppo")
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
