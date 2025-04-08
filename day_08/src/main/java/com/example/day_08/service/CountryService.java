package com.example.day_08.service;

import com.example.day_08.entity.Country;

public interface CountryService {
    Country findCountryExist(String slug);
    Country createCountryByName(String name);
}
