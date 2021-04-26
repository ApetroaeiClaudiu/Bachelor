package com.claudiu.microservicemoviemanagement.controller;

import com.claudiu.microservicemoviemanagement.intercomm.UserClient;
import com.claudiu.microservicemoviemanagement.model.Rating;
import com.claudiu.microservicemoviemanagement.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private MovieService movieService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment environment;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort(){
        return "Service is working at port: " + environment.getProperty("local.server.port");
    }

    @GetMapping("/server/instances")
    public ResponseEntity<?> getInstances(){
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId), HttpStatus.OK);
    }

    @GetMapping("/service/user/{userId}")
    public ResponseEntity<?> findRatingsOfUser(@PathVariable Long userId){
        return new ResponseEntity<>(movieService.findUserRatings(userId), HttpStatus.OK);
    }

    @GetMapping("/service/movies")
    public ResponseEntity<?> findAllMovies(){
        return new ResponseEntity<>(movieService.allMovies(),HttpStatus.OK);
    }

    @PostMapping("/service/rate")
    public ResponseEntity<?> saveRating(@RequestBody Rating rating){
        rating.setMovie(movieService.findMovieById(rating.getMovie().getId()));
        return new ResponseEntity<>(movieService.saveRating(rating),HttpStatus.OK);
    }

    @GetMapping("/service/ratings/{movieId}")
    public ResponseEntity<?> findRatingsOfMovie(@PathVariable Long movieId){
        return new ResponseEntity<>(movieService.findRatingsOfMovie(movieId),HttpStatus.OK);
    }

    @GetMapping("/service/movie/{movieId}")
    public ResponseEntity<?> findStudentsOfCourse(@PathVariable Long movieId){
        List<Rating> ratings = movieService.findRatingsOfMovie(movieId);
        if(CollectionUtils.isEmpty(ratings)){
            return ResponseEntity.notFound().build();
        }
        List<Long> userIdList = ratings.parallelStream().map(t -> t.getUserId()).collect(Collectors.toList());
        List<String> users = userClient.getEmails(userIdList);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
