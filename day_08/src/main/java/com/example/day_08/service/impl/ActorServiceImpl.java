package com.example.day_08.service.impl;

import com.example.day_08.entity.Actor;
import com.example.day_08.repository.ActorRepository;
import com.example.day_08.service.ActorService;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;

    @Override
    public Actor findActorExist(String slug) {
        return actorRepository.findBySlug(slug);
    }

    @Override
    public Actor createActorByName(String name) {
        Actor actor = new Actor();
        Slugify slugify = Slugify.builder().build();
        actor.setName(name);
        actor.setSlug(slugify.slugify(name));
        actor.setCreatedAt(LocalDateTime.now());
        actor.setUpdatedAt(LocalDateTime.now());
        return actorRepository.save(actor);
    }
}
