package com.ratiose.testtask.service.impl;

import com.ratiose.testtask.entity.FavoriteActor;
import com.ratiose.testtask.repository.FavoriteActorRepository;
import com.ratiose.testtask.service.FavoriteActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteActorServiceImpl implements FavoriteActorService {

    @Autowired
    private FavoriteActorRepository favoriteActorRepository;

    @Override
    public List<FavoriteActor> findFavoriteActors(String email) {
        return favoriteActorRepository.findByEmail(email);
    }

    @Override
    public FavoriteActor addFavoriteActor(String email, String actorName) {
        FavoriteActor favoriteActor = favoriteActorRepository.findByEmailAndActorName(email, actorName);
        if (favoriteActor != null) {
            return null;
        }
        favoriteActor = createFavoriteActor(email, actorName);
        return favoriteActorRepository.save(favoriteActor);
    }

    @Override
    public boolean removeFavoriteActor(String email, String actorName) {
        FavoriteActor favoriteActor = favoriteActorRepository.findByEmailAndActorName(email, actorName);
        if (favoriteActor != null) {
            favoriteActorRepository.delete(favoriteActor);
            return true;
        }
        return false;
    }

    private FavoriteActor createFavoriteActor(String email, String actorName) {
        FavoriteActor favoriteActor = new FavoriteActor();
        favoriteActor.setEmail(email);
        favoriteActor.setActorName(actorName);
        return favoriteActor;
    }
}
