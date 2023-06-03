package com.example.jobs.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AnnouncementModel {
    String id;
    Integer index;
    String jobName;
    String jobType;
    String jobLevel;
    LocalDate datePosted;
    LocalDate dateEnded;
    String description;
    String responsability;
    String requirement;
    String benefit;
    String skills;
    Integer numberOfCandidates;
    String available;
    String companyName;
    String companyDescription;
    String companyEmail;
    String companyPhone;
    String companyCity;
    String companyCountry;
}
