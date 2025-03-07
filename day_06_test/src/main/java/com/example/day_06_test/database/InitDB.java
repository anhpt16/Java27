package com.example.day_06_test.database;

import com.example.day_06_test.util.IFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {

    @Autowired
    private IFileReader csvFileReader;

    @Override
    public void run(String... args) throws Exception {
        ProductDB.products = csvFileReader.readFile("product.csv");
        System.out.println(ProductDB.products.size());
        System.out.println(ProductDB.products.get(0));
    }
}
