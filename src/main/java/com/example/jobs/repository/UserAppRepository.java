package com.example.jobs.repository;

import com.example.jobs.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, String> {

    Optional<UserApp> getUserAppByEmail(String email);

    @Query("select c from UserApp c where c.email = ?1")
    List<UserApp> getByEmail(String email);

}
