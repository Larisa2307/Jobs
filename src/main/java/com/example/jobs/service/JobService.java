package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import com.example.jobs.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class JobService {

    final JobRepository jobsRepository;

    public void save(Job job) {
        jobsRepository.save(job);
    }

    public void delete(Job job) {
        jobsRepository.delete(job);
    }

    public Job getById(String id) {
        return jobsRepository.findById(id).get();
    }

    public List<Job> getJobsByCompany(Company company) {
        return jobsRepository.findJobsByCompany(company);
    }

}
