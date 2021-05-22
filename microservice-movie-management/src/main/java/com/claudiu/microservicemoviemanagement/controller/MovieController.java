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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/service/all")
    public ResponseEntity<?> findAllMovies(){
        return new ResponseEntity<>(movieService.allMovies(),HttpStatus.OK);
    }

    @PostMapping("/service/rate")
    public ResponseEntity<?> saveRating(@RequestBody Rating rating){
        Rating r = movieService.findRating(rating.getUserId(),rating.getMovie().getId());
        if(r == null ){
            rating.setMovie(movieService.findMovieById(rating.getMovie().getId()));
            return new ResponseEntity<>(movieService.saveRating(rating),HttpStatus.OK);
        }
        r.setRatingValue(rating.getRatingValue());
        r.setMovie(movieService.findMovieById(rating.getMovie().getId()));
        return new ResponseEntity<>(movieService.saveRating(r),HttpStatus.OK);
    }

    @GetMapping("/service/ratings/{movieId}")
    public ResponseEntity<?> findRatingsOfMovie(@PathVariable Long movieId){
        return new ResponseEntity<>(movieService.findRatingsOfMovie(movieId),HttpStatus.OK);
    }

    @GetMapping("/service/movie/{movieId}")
    public ResponseEntity<?> findUsersOfMovie(@PathVariable Long movieId){
        List<Rating> ratings = movieService.findRatingsOfMovie(movieId);
        if(CollectionUtils.isEmpty(ratings)){
            return ResponseEntity.notFound().build();
        }
        List<String> finalList = new ArrayList<>();
        List<Long> userIdList = ratings.parallelStream().map(t -> t.getUserId()).collect(Collectors.toList());
        List<String> users = userClient.getUsers(userIdList);
        for(int i = 0; i < users.size(); i ++) {
            String name = users.get(i).split(",")[0];
            String id = users.get(i).split(",")[1];
            finalList.add(name);
            finalList.add(id);
            Long userId = Long.parseLong(id);
            finalList.add(String.valueOf(movieService.findValue(movieId,userId)));
        }
        return new ResponseEntity<>(finalList,HttpStatus.OK);
    }
}
