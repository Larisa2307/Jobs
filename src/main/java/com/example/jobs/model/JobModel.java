package com.example.jobs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobModel {
    String id;
    Integer index;
    String jobName;
    String jobType;
    String jobLevel;
    Long salary;
    Integer numberOfVacantSpots;
    String company;
}
