package com.claudiu.microservicemoviemanagement.repository;

import com.claudiu.microservicemoviemanagement.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findAllByUserId(Long userId);

    List<Rating> findAllByMovieId(Long movieId);

    @Query("select r.ratingValue from Rating r where r.userId=(:userId) and r.movie.id=(:movieId)")
    int findValue(Long movieId, Long userId);


    @Query("select r from Rating r where r.userId=(:userId) and r.movie.id=(:movieId)")
    Rating findRating(Long userId, Long movieId);

}
