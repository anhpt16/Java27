package com.example.day_08.service;

import com.example.day_08.entity.Director;

public interface DirectorService {
    Director findDirectorExist(String slug);
    Director createDirectorByName(String name);
}
