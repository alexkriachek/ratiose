package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.FavoriteActor;

import java.util.List;

public interface FavoriteActorService {
    List<FavoriteActor> findFavoriteActors(String email);
    FavoriteActor addFavoriteActor(String email, String actorName);
    boolean removeFavoriteActor(String email, String actorName);
}
