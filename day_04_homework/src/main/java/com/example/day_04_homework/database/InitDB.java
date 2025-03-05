package com.example.day_04_homework.database;

import com.example.day_04_homework.util.IFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {

    @Autowired
    private IFileReader csvFileReader;

    @Override
    public void run(String... args) throws Exception {
        PeopleDB.peoples = csvFileReader.readFile("people.csv");
        System.out.println(PeopleDB.peoples.size());
        System.out.println(PeopleDB.peoples.get(0).toString());
    }
}
