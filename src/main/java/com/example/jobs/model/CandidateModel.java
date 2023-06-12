package com.example.jobs.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CandidateModel {
    String id;
    String jobName;
    String companyName;
    String jobType;
    String jobLevel;
    LocalDate datePosted;
    LocalDate dateEnded;
    String available;
    String accepted;
}
