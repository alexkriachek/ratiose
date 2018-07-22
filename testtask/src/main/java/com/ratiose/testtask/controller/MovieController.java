package com.ratiose.testtask.controller;

import com.mashape.unirest.http.JsonNode;
import com.ratiose.testtask.entity.FavoriteActor;
import com.ratiose.testtask.entity.WatchedMovie;
import com.ratiose.testtask.service.FavoriteActorService;
import com.ratiose.testtask.service.UserService;
import com.ratiose.testtask.service.WatchedMovieService;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import com.ratiose.testtask.util.MovieUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private UserService userService;

    @Autowired
    private WatchedMovieService watchedMovieService;

    @Autowired
    private FavoriteActorService favoriteActorService;

    @Autowired
    private TmdbApi tmdbApi;

    @RequestMapping(value = "/popular", method = POST)
    public ResponseEntity popular(@RequestParam String email,
                                  @RequestParam String password,
                                  HttpSession session) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String popularMovies = tmdbApi.popularMovies();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }

    @RequestMapping(value = "/watchedMovie", method = POST)
    public ResponseEntity addWatchedMovie(@RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String movieName,
                                  HttpSession session) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (watchedMovieService.addWatchedMovie(email, movieName) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/unwatchedMovie", method = POST)
    public ResponseEntity removeWatchedMovie(@RequestParam String email,
                                          @RequestParam String password,
                                          @RequestParam String movieName,
                                          HttpSession session) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (watchedMovieService.removeWatchedMovie(email, movieName)) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/findMovies", method = POST)
    public ResponseEntity searchAllUnwatchedMoviesWithFavoriteActors(@RequestParam String email,
                                          @RequestParam String password,
                                          @RequestParam String year,
                                          @RequestParam String month,
                                          HttpSession session) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<JSONObject> resultList = getUnwatchedMovies(email, year, month);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(resultList.toString());
    }

    private List<JSONObject> getUnwatchedMovies(String email, String year, String month) {
        List<String> watchedMovieList = watchedMovieService.findWatchedMovies(email).stream()
                .map(WatchedMovie::getMovieName)
                .collect(Collectors.toList());

        List<JsonNode> moviesWithFavoriteActors = favoriteActorService.findFavoriteActors(email).stream()
                .map(it -> tmdbApi.searchMoviesByActor(it.getActorName()))
                .collect(Collectors.toList());

        return MovieUtils.getUnwatchedMoviesWithFavoriteActorsByDate(moviesWithFavoriteActors, watchedMovieList, year, month);
    }
}
