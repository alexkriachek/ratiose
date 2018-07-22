package com.ratiose.testtask.service.impl;

import com.ratiose.testtask.entity.FavoriteActor;
import com.ratiose.testtask.entity.WatchedMovie;
import com.ratiose.testtask.repository.WatchedMovieRepository;
import com.ratiose.testtask.service.WatchedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchedMovieServiceImpl implements WatchedMovieService {

    @Autowired
    private WatchedMovieRepository watchedMovieRepository;

    @Override
    public WatchedMovie
    addWatchedMovie(String email, String movieName) {
        WatchedMovie watchedMovie = watchedMovieRepository.findByEmailAndMovieName(email, movieName);
        if (watchedMovie != null) {
            return null;
        }
        watchedMovie = createWatchedMovie(email, movieName);
        return watchedMovieRepository.save(watchedMovie);
    }

    @Override
    public boolean removeWatchedMovie(String email, String movieName) {
        WatchedMovie watchedMovie = watchedMovieRepository.findByEmailAndMovieName(email, movieName);
        if (watchedMovie != null) {
            watchedMovieRepository.delete(watchedMovie);
            return true;
        }
        return false;
    }

    @Override
    public List<WatchedMovie> findWatchedMovies(String email) {
        return watchedMovieRepository.findByEmail(email);
    }

    private WatchedMovie createWatchedMovie(String email, String movieName) {
        WatchedMovie watchedMovie = new WatchedMovie();
        watchedMovie.setEmail(email);
        watchedMovie.setMovieName(movieName);
        return watchedMovie;
    }
}
