package com.claudiu.microserviceusermanagement.controller;

import com.claudiu.microserviceusermanagement.model.Role;
import com.claudiu.microserviceusermanagement.model.User;
import com.claudiu.microserviceusermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort(){
        return "Service port number : " + env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances(){
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId), HttpStatus.OK);
    }

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices(){
        return new ResponseEntity<>(discoveryClient.getServices(), HttpStatus.OK);
    }

    @PostMapping("/service/register")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if(userService.findByEmail(user.getEmail()) != null){
            //409
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //default
        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.OK);
    }

    @GetMapping("/service/login")
    public ResponseEntity<?> getUser(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal == null || principal.getName() == null){
            //logout successful
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.findByEmail(principal.getName()),HttpStatus.OK);
    }

    @PostMapping("/service/emails")
    public ResponseEntity<?> getEmailsOfUsers(@RequestBody List<Long> idList){
        return new ResponseEntity<>(userService.findUsers(idList),HttpStatus.OK);
    }
}

