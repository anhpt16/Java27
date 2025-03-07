package com.example.day_06_test.controller;

import com.example.day_06_test.model.PageResponse;
import com.example.day_06_test.model.Product;
import com.example.day_06_test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String getAllProduct(
        @RequestParam (required = false, defaultValue = "1") int page,
        @RequestParam (required = false, defaultValue = "15") int size,
        @RequestParam (required = false) String keyword,
        Model model
    ) {
        // Check page
        if (page <= 0 || size <= 0) {
            return "fail";
        }
        PageResponse<Product> pageResponse = null;
        if (keyword == null) {
            pageResponse = productService.getProduct(page, size);
        } else {
            pageResponse = productService.getProduct(keyword, page, size);
        }
        if (pageResponse == null) {
            return "fail";
        }
        model.addAttribute("pageResponse", pageResponse);
        return "product_list";
    }

    @GetMapping("/product/{id}")
    public String getProductById(
        @PathVariable int id,
        Model model
    ) {
        if (id <= 0) {
            return "fail";
        }
        Product product = productService.getProductById(id);
        if (product == null) {
            return "fail";
        }
        List<Product> productRelated = productService.getProductRelated(product.getName().substring(0, 1), 5, product.getId());
        model.addAttribute("product", product);
        model.addAttribute("productRelated", productRelated);
        return "product_detail";
    }
}
