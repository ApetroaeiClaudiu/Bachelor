package com.claudiu.microserviceusermanagement.repository;

import com.claudiu.microserviceusermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    @Query("select u.email from User u where u.id in (:pIdList)")
    List<String> findUsers(@Param("pIdList") List<Long> idList);
}
