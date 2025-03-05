package com.example.day_04_homework.util;

import com.example.day_04_homework.model.People;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileReader implements IFileReader{
    @Override
    public List<People> readFile(String path) {
        List<People> peoples = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] line;
            reader.readNext(); // Bỏ qua dòng tiêu đề (nếu có)
            while ((line = reader.readNext()) != null) {
                int id = Integer.parseInt(line[0]);
                String first_name = line[1];
                String last_name = line[2];
                String email = line[3];
                String gender = line[4];
                String city = line[5];
                String job = line[6];
                int salary = Integer.parseInt(line[7]);

                People people = new People(id, first_name, last_name, email, gender, city, job, salary);
                peoples.add(people);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return peoples;
    }
}
