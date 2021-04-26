package com.claudiu.microserviceusermanagement.service;

import com.claudiu.microserviceusermanagement.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User findByEmail(String email);

    List<String> findUsers(List<Long> idList);
}
