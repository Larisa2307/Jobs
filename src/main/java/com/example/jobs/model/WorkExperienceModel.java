package com.example.jobs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkExperienceModel {
    String id;
    String companyName;
    String role;
    String startDate;
    String endDate;
    String projectsDescription;

}
