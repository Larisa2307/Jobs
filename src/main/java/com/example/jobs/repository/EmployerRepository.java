package com.example.jobs.repository;

import com.example.jobs.entity.Employer;
import com.example.jobs.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, String> {

    @Query("select c from Employer c where c.uniqueCode = ?1")
    List<Employer> getByUniqueCode(String uniqueCode);

    Optional<Employer> findByEmailAndPassword(String email, String password);

    Employer getByUsername(String username);
}
