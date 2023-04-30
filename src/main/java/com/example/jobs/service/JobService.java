package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import com.example.jobs.mapper.JobMapper;
import com.example.jobs.model.JobModel;
import com.example.jobs.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class JobService {

    final JobRepository jobsRepository;

    public void save(Job job) {
        jobsRepository.save(job);
    }

    public Job getById(String id) {
        return jobsRepository.findById(id).get();
    }

    public List<Job> getJobsByCompany(Company company) {
        return jobsRepository.findJobsByCompany(company);
    }

    private Job toJob(JobModel jobModel, Company company) {
        Job job = JobMapper.toEntry(jobModel);
        job.setCompany(company);
        return job;
    }

    private JobModel toJobModel(Job job, int index) {
        return JobMapper.toModel(job, index);
    }

    public void saveNewJob(JobModel jobModel, Company company) {
        Job job = toJob(jobModel, company);
        job.setId(jobModel.getId());
        jobsRepository.save(job);

    }

    public List<JobModel> getJobModelList(Company company) {
        var job = jobsRepository.findJobsByCompany(company);

        var index = new AtomicInteger(1);
        return job.stream()
                .map(announcement -> toJobModel(announcement, index.getAndIncrement()))
                .toList();
    }
}
