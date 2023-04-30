package com.example.jobs.repository;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, String> {

    List<Job> findJobsByCompany(Company company);

}
