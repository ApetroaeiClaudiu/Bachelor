package com.claudiu.microservicemoviemanagement.repository;

import com.claudiu.microservicemoviemanagement.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
