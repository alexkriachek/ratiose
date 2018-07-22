package com.ratiose.testtask.service.tmdb;

import com.mashape.unirest.http.JsonNode;

public interface TmdbApi {
    String popularMovies();
    JsonNode searchMoviesByActor(String actorName);
}
