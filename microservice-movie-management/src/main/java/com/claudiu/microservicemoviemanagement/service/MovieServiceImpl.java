package com.claudiu.microservicemoviemanagement.service;

import com.claudiu.microservicemoviemanagement.model.Movie;
import com.claudiu.microservicemoviemanagement.model.Rating;
import com.claudiu.microservicemoviemanagement.repository.MovieRepository;
import com.claudiu.microservicemoviemanagement.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    @Override
    public Movie findMovieById(Long id){
        return movieRepository.findById(id).orElse(null);
    }

    //all the ratings of an user
    @Override
    public List<Rating> findUserRatings(Long userId){
        return ratingRepository.findAllByUserId(userId);
    }

    //all the rating of a movie
    @Override
    public List<Rating> findRatingsOfMovie(Long movieId){
        return ratingRepository.findAllByMovieId(movieId);
    }

    @Override
    public Rating saveRating(Rating rating){
        Rating newRating = ratingRepository.save(rating);
        Movie newMovie = updateMovie(findMovieById(rating.getMovie().getId()));
        newRating.setMovie(newMovie);
        ratingRepository.save(newRating);
        return newRating;
    }

    @Override
    public Movie updateMovie(Movie movie){
        List<Rating> movieRatings = findRatingsOfMovie(movie.getId());
        int sum = 0;
        for(Rating r : movieRatings){
            sum=sum+r.getRatingValue();
        }
        if(sum!=0){
            movie.setAverage((float)sum/movieRatings.size());
        }
        return movieRepository.save(movie);
    }

    @Override
    public int findValue(Long movieId, Long userId){
        return ratingRepository.findValue(movieId,userId);
    }

    @Override
    public Rating findRating(Long userId,Long movieId){return ratingRepository.findRating(userId, movieId);}
}
