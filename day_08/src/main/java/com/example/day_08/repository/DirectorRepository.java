package com.example.day_08.repository;

import com.example.day_08.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

    Director findBySlug(String slug);
}
