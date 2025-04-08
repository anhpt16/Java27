package com.example.day_08.service;

import com.example.day_08.entity.Actor;

public interface ActorService {
    Actor findActorExist(String slug);
    Actor createActorByName(String name);
}
