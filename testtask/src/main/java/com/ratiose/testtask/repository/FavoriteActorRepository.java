package com.ratiose.testtask.repository;

import com.ratiose.testtask.entity.FavoriteActor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteActorRepository extends CrudRepository<FavoriteActor, Long> {
    List<FavoriteActor> findByEmail(String email);
    FavoriteActor findByEmailAndActorName(String email, String actorName);
}
