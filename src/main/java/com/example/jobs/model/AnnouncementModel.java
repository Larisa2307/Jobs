package com.example.jobs.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AnnouncementModel {
    String id;
    Integer index;
    String jobName;
    String jobType;
    String jobLevel;
    LocalDate datePosted;
    String description;
    String responsability;
    String requirement;
    String benefit;
    String industries;
    List<String> announcementSkills;
}
