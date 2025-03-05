package com.example.day_04_homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String city;
    private String job;
    private int salary;
}
