package com.example.jobs.repository;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, String> {

    List<Job> findJobsByCompany(Company company);

    @Query("select j.name from Job j where j.company = ?1")
    List<String> findJobNamesByCompany(Company company);

    @Query("select j.type from Job j where j.company = ?1")
    List<String> findJobTypesByCompany(Company company);

    @Query("select j.level from Job j where j.company = ?1")
    List<String> findJobLevelsByCompany(Company company);
}
