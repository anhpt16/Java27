package com.example.day_08.repository;

import com.example.day_08.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country findBySlug(String slug);
}
