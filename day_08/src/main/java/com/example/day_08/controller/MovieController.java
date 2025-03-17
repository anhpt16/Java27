package com.example.day_08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/phim")
public class MovieController {

    @GetMapping("/{slug}")
    public String movie() {

        return "movie_detail";
    }
}
