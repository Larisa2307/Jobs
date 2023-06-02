package com.example.jobs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EducationModel {
    String id;
    String university;
    String degree;
    String speciality;
    String startDate;
    String endDate;
}
