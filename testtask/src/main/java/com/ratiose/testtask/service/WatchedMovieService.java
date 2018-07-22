package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.WatchedMovie;

import java.util.List;

public interface WatchedMovieService {
    WatchedMovie addWatchedMovie(String email, String movieName);
    boolean removeWatchedMovie(String email, String movieName);
    List<WatchedMovie> findWatchedMovies(String email);
}
