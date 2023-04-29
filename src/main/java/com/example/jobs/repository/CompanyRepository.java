package com.example.jobs.repository;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("select c from Company c where c.uniqueCode = ?1")
    List<Company> getByUniqueCode(String uniqueCode);


}
