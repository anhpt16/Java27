package com.example.day_06_test.util;

import com.example.day_06_test.model.Product;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CsvFileReader implements IFileReader{
    @Override
    public List<Product> readFile(String path) {
        List<Product> products = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] line;
            reader.readNext(); // Bỏ qua dòng tiêu đề (nếu có)
            while ((line = reader.readNext()) != null) {
                Random random = new Random();
                int randomNumber = random.nextInt(10) + 1;

                int id = Integer.parseInt(line[0]);
                String name = line[1];
                String description = line[2];
                String thumbnail = randomNumber + ".jpg";
                int price = Integer.parseInt(line[3]);
                double rating = Double.parseDouble(line[4]);
                int priceDiscount = 0;
                if (line.length > 5 && !line[5].isEmpty()) {
                    priceDiscount = Integer.parseInt(line[5]);
                }

                Product product = new Product(id, name, description, thumbnail, price, rating, priceDiscount);
                products.add(product);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return products;
    }
}
