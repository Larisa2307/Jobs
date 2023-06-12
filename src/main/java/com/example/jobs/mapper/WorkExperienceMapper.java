package com.example.jobs.mapper;

import com.example.jobs.entity.WorkExperience;
import com.example.jobs.model.WorkExperienceModel;
import com.example.jobs.util.DateFormat;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class WorkExperienceMapper {
    public static WorkExperienceModel toModel(final WorkExperience workExperience) {
        return WorkExperienceModel.builder()
                .id(workExperience.getId())
                .companyName(workExperience.getCompanyName())
                .startDate(DateFormat.formatBasic(workExperience.getStartDate()))
                .endDate(DateFormat.formatBasic(workExperience.getEndDate()))
                .role(workExperience.getRole())
                .projectsDescription(workExperience.getProjectsDescription())
                .build();
    }

}
