package com.ratiose.testtask.repository;

import com.ratiose.testtask.entity.WatchedMovie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WatchedMovieRepository extends CrudRepository<WatchedMovie, Long> {
    List<WatchedMovie> findByEmail(String email);
    WatchedMovie findByEmailAndMovieName(String email, String movieName);
}
