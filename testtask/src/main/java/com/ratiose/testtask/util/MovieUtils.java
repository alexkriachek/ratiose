package com.ratiose.testtask.util;

import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieUtils {
    public static List<JSONObject> getUnwatchedMoviesWithFavoriteActorsByDate(List<JsonNode> moviesWithFavoriteActors,
                                                                              List<String> watchedMovieList,
                                                                              String year, String month) {

        List<JSONObject> unwatchedMovies = extractAllFilms(moviesWithFavoriteActors).stream()
                .filter(it -> !watchedMovieList.contains(it.getString("title")))
                .collect(Collectors.toList());

        return unwatchedMovies.stream()
                .filter(it -> isReleaseDateSatisfyRequest(it.getString("release_date"), year, month))
                .collect(Collectors.toList());
    }

    private static List<JSONObject> extractAllFilms(List<JsonNode> moviesWithFavoriteActors) {
        List<JSONObject> resultList = new ArrayList<>();

        for(JsonNode jsonNode : moviesWithFavoriteActors) {
            JSONArray resultsArray = jsonNode.getObject().getJSONArray("results");
            for(int i = 0; i < resultsArray.length(); i++) {
                JSONArray jsonArray = resultsArray.getJSONObject(i).getJSONArray("known_for");
                for(int j = 0; j < jsonArray.length(); j++) {
                    resultList.add(jsonArray.getJSONObject(j));
                }
            }
        }
        return resultList;
    }

    private static boolean isReleaseDateSatisfyRequest(String releaseDate, String year, String month) {
        if (year == null && month == null) {
            return true;
        }

        String[] releaseDateArray = releaseDate.split("-");
        return releaseDateArray[0].equals(year) && releaseDateArray[1].equals(month);
    }
}
