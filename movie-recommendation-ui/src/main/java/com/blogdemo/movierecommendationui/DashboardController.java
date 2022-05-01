package com.blogdemo.movierecommendationui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
	private ReactiveCircuitBreakerFactory circuitBreakerFactory;

    @RequestMapping("/dashboard")
	public String GetMovieRecommendations(@RequestParam(defaultValue = "1") Integer userId, Model m) {
        ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("movierecommendations-cb");

        Mono<List<Movie>> rate = rcb.run(webClientBuilder.build().get()
            .uri("http://movie-recommendation-service/movies/" + userId)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Movie>>() {})
            , throwable -> getDefaultMovies());
		
		System.out.println("userId: " + userId);
		m.addAttribute("movies", rate.block());
		return "dashboard";
	}

    private Mono<List<Movie>> getDefaultMovies() {
        System.out.println("Fallback method called");

        return Mono.just(Arrays.asList(
                new Movie("The Shawshank Redemption", 1994),
                new Movie("The Godfather", 1972),
                new Movie("The Dark Knight", 2008)));
    }
    
}
