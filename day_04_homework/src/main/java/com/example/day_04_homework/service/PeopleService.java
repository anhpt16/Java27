package com.example.day_04_homework.service;

import com.example.day_04_homework.model.People;

import java.util.List;
import java.util.Map;

public interface PeopleService {

    Map<String, List<People>> getPeopleGroupByCity();
    Map<String, Long> getJobCount();
    List<People> getPeopleHigherAverageSalary();
    List<People> getPeopleHighestName();
    People getPeopleById(int id);
    List<People> getPeopleByFilter(String keyword, int page, int size);
    List<People> getAllPeople(int page, int size);
    List<People> getRelatedPeople(String gender, int size, int id);
}
