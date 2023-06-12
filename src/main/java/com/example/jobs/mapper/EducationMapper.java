package com.example.jobs.mapper;

import com.example.jobs.entity.Education;
import com.example.jobs.model.EducationModel;
import com.example.jobs.util.DateFormat;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EducationMapper {
    public static EducationModel toModel(Education education) {
        return EducationModel.builder()
                .id(education.getId())
                .university(education.getUniversity())
                .degree(education.getDegree())
                .startDate(DateFormat.formatBasic(education.getStartDate()))
                .endDate(DateFormat.formatBasic(education.getEndDate()))
                .speciality(education.getSpeciality())
                .build();
    }

}
