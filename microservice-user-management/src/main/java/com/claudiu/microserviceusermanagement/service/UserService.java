package com.claudiu.microserviceusermanagement.service;

import com.claudiu.microserviceusermanagement.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User saveUser(User user);

    User findByEmail(String email);

    User findByUsername(String username);

    List<String> findUsers(List<Long> idList);

    List<String> findUsersFull(List<Long> idList);
}
