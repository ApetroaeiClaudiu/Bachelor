package com.claudiu.microservicemoviemanagement.repository;

import com.claudiu.microservicemoviemanagement.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findAllByUserId(Long userId);

    List<Rating> findAllByMovieId(Long movieId);
}
