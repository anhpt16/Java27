package com.example.day_04_homework.service.impl;

import com.example.day_04_homework.dao.PeopleDAO;
import com.example.day_04_homework.model.People;
import com.example.day_04_homework.service.PeopleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PeopleServiceImpl implements PeopleService {
    private final PeopleDAO peopleDAO;

    @Override
    public Map<String, List<People>> getPeopleGroupByCity() {
        List<People> peopleList = peopleDAO.getAllPeople();
        if (peopleList.size() == 0) {
            return null;
        }
        return peopleList.stream()
            .collect(Collectors.groupingBy(People::getCity));
    }

    @Override
    public Map<String, Long> getJobCount() {
        List<People> peopleList = peopleDAO.getAllPeople();
        if (peopleList.size() == 0) {
            return null;
        }
        return peopleList.stream()
            .collect(Collectors.groupingBy(People::getJob, Collectors.counting()));
    }

    @Override
    public List<People> getPeopleHigherAverageSalary() {
        List<People> peopleList = peopleDAO.getAllPeople();
        if (peopleList.size() == 0) {
            return null;
        }
        double averageSalary = peopleList.stream()
            .mapToInt(People::getSalary)
            .average()
            .orElse(0);
        System.out.println("Mức lương trung bình: " + averageSalary);
        return peopleList.stream()
            .filter(people -> people.getSalary() > averageSalary)
            .collect(Collectors.toList());
    }

    @Override
    public List<People> getPeopleHighestName() {
        List<People> peopleList = peopleDAO.getAllPeople();
        if (peopleList.size() == 0) {
            return null;
        }
        int maxLength = peopleList.stream()
            .map(people -> (people.getFirst_name()+ " " + people.getLast_name()).length())
            .max(Integer::compare)
            .orElse(0);
        return peopleList.stream()
            .filter(people -> (people.getFirst_name()+ " " + people.getLast_name()).length() == maxLength)
            .collect(Collectors.toList());
    }

}
