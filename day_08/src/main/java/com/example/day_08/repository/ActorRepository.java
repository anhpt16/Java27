package com.example.day_08.repository;

import com.example.day_08.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Actor findBySlug(String slug);
}
