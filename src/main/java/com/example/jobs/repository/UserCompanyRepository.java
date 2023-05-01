package com.example.jobs.repository;

import com.example.jobs.entity.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompany, String> {

    Optional<UserCompany> findByEmailAndPassword(String email, String password);

    @Query("select c from UserCompany c where c.email = ?1")
    List<UserCompany> getByEmail(String email);

}