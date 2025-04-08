package com.example.day_08.controller;

import com.example.day_08.model.response.ShortMovieResponse;
import com.example.day_08.service.FavoriteService;
import com.example.day_08.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
@RequestMapping("/phim-yeu-thich")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;

    @GetMapping
    public String getFavoriteMovies(
        @RequestParam (name = "page", defaultValue = "1") int page,
        @RequestParam (name = "size", defaultValue = "18") int size,
        Model model
    ) {
        Integer defautlUserId = 1;
        Page<ShortMovieResponse> favoriteMovies = favoriteService.getFavoriteMoviesByUser(defautlUserId, page - 1, size);
        model.addAttribute("favoriteMovies", favoriteMovies);
        return "movies_favorite";
    }
}
