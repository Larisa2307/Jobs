package com.example.jobs.mapper;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Job;
import com.example.jobs.model.AnnouncementModel;
import com.example.jobs.model.JobModel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class JobMapper {
    public static JobModel toModel(final Job job, final int index) {
        return JobModel.builder()
                .id(job.getId())
                .jobName(job.getName())
                .jobType(job.getType())
                .jobLevel(job.getLevel())
                .salary(job.getSalary())
                .numberOfVacantSpots(job.getNumberOfVacantSpots())
                .index(index)
                .build();
    }

    public static Job toEntry(final JobModel jobModel) {
        return Job.builder()
                .name(jobModel.getJobName())
                .type(jobModel.getJobType())
                .level(jobModel.getJobLevel())
                .salary(jobModel.getSalary())
                .numberOfVacantSpots(jobModel.getNumberOfVacantSpots())
                .build();
    }
}
