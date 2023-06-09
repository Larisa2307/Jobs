package com.example.jobs.repository;

import com.example.jobs.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("select c from Company c where c.uniqueCode = ?1")
    List<Company> getByUniqueCode(String uniqueCode);

    List<Company> getByEmail(String email);

    @Query("select c from Company c where c.email = ?1 and c.id <> ?2")
    Optional<Company> getByEmailAndDifferentId(String email, String id);
}
