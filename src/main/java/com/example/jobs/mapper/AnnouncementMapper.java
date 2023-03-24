package com.example.jobs.mapper;

import com.example.jobs.entity.Announcement;
import com.example.jobs.model.AnnouncementModel;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AnnouncementMapper {

    public static AnnouncementModel toModel(final Announcement announcement, final List<String> skills, final int index) {
        return AnnouncementModel.builder()
                .id(announcement.getId())
                .jobName(announcement.getJobName())
                .jobType(announcement.getJobType())
                .jobLevel(announcement.getJobLevel())
                .description(announcement.getDescription())
                .industries(announcement.getIndustries().getName())
                .benefit(announcement.getBenefit())
                .requirement(announcement.getRequirement())
                .responsability(announcement.getResponsability())
                .index(index)
                .announcementSkills(skills)
                .build();
    }

    public static Announcement toEntry(final AnnouncementModel announcementModel) {
        return Announcement.builder()
                .jobName(announcementModel.getJobName())
                .jobType(announcementModel.getJobType())
                .jobLevel(announcementModel.getJobLevel())
                .description(announcementModel.getDescription())
                .benefit(announcementModel.getBenefit())
                .requirement(announcementModel.getRequirement())
                .responsability(announcementModel.getResponsability())
                .build();
    }
}
