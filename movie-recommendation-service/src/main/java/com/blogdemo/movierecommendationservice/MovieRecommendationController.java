package com.blogdemo.movierecommendationservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieRecommendationController {
    Map<Integer, List<Movie>> userToMovies = new HashMap<>();

    public MovieRecommendationController() {
        List<Movie> movies = Arrays.asList(
                new Movie("The Avengers", 2012),
                new Movie("Avengers: Infinity War", 2018),
                new Movie("Avengers: Endgame", 2019)
        );
        userToMovies.put(1, movies);
    }

    @RequestMapping("/movies/{userId}")
    public List<Movie> getMovies(@PathVariable int userId) {
        System.out.println("User requested: " + userId);
        return userToMovies.get(1);
    }
    
}
