package com.example.day_04_homework.service;

import com.example.day_04_homework.model.People;

import java.util.List;
import java.util.Map;

public interface PeopleService {

    Map<String, List<People>> getPeopleGroupByCity();
    Map<String, Long> getJobCount();
    List<People> getPeopleHigherAverageSalary();
    List<People> getPeopleHighestName();
}
