package com.example.day_08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/phim-chieu-rap")
    public String movieCinema() {

        return "movies_cinema";
    }

    @GetMapping("/phim-bo")
    public String movieSeries() {

        return "movies_series";
    }

    @GetMapping("/phim-le")
    public String movieSingle() {

        return "movies_single";
    }

    @GetMapping("/cua-hang")
    public String movieStore() {

        return "movies_store";
    }
}
