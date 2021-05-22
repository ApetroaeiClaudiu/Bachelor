package com.claudiu.microservicemoviemanagement.intercomm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient("user-service")
public interface UserClient {

    @RequestMapping(method = RequestMethod.POST, value = "/service/emails", consumes = "application/json")
    List<String> getEmails(@RequestBody List<Long> userIdList);

    @RequestMapping(method = RequestMethod.POST, value = "/service/users", consumes = "application/json")
    List<String> getUsers(@RequestBody List<Long> userIdList);
}