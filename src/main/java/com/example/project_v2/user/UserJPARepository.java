package com.example.project_v2.user;

import com.example.project_v2.apply.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    Optional<User> findByUsername(@Param("username") String username);

}
