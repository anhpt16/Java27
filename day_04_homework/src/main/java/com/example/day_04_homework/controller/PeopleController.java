package com.example.day_04_homework.controller;

import com.example.day_04_homework.model.People;
import com.example.day_04_homework.service.PeopleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;

    @GetMapping("/groupPeopleByCity")
    public String groupPeopleByCity(Model model) {
        Map<String, List<People>> results = peopleService.getPeopleGroupByCity();
        model.addAttribute("results", results != null ? results : Collections.emptyMap());
        return "home";
    }

    @GetMapping("/groupJobByCount")
    public String groupJobByCount(Model model) {
        Map<String, Long> resutls = peopleService.getJobCount();
        model.addAttribute("results", resutls != null ? resutls : Collections.emptyMap());
        return "home1";
    }

    @GetMapping("/aboveAverageSalary")
    public String aboveAverageSalary(Model model) {
        List<People> results = peopleService.getPeopleHigherAverageSalary();
        model.addAttribute("results", results != null ? results : Collections.emptyList());
        return "home2";
    }

    @GetMapping("/longestName")
    public String longestName(Model model) {
        List<People> results = peopleService.getPeopleHighestName();
        model.addAttribute("results", results != null ? results : Collections.emptyList());
        return "home3";
    }
}
