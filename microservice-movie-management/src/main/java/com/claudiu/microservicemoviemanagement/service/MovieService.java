package com.claudiu.microservicemoviemanagement.service;

import com.claudiu.microservicemoviemanagement.model.Movie;
import com.claudiu.microservicemoviemanagement.model.Rating;

import java.util.List;

public interface MovieService {
    List<Movie> allMovies();

    Movie findMovieById(Long id);

    List<Rating> findUserRatings(Long userId);

    List<Rating> findRatingsOfMovie(Long movieId);

    Rating saveRating(Rating rating);

    Movie updateMovie(Movie movie);
}
