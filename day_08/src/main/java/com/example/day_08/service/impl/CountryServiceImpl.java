package com.example.day_08.service.impl;

import com.example.day_08.entity.Country;
import com.example.day_08.repository.CountryRepository;
import com.example.day_08.service.CountryService;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Country findCountryExist(String slug) {
        return countryRepository.findBySlug(slug);
    }

    @Override
    public Country createCountryByName(String name) {
        Country country = new Country();
        Slugify slugify = Slugify.builder().build();
        country.setName(name);
        country.setSlug(slugify.slugify(name));
        country.setCreatedAt(LocalDateTime.now());
        country.setUpdatedAt(LocalDateTime.now());
        return countryRepository.save(country);
    }
}
