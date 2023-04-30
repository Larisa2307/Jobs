package com.example.jobs.mapper;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Job;
import com.example.jobs.model.AnnouncementModel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AnnouncementMapper {

    public static AnnouncementModel toModel(final Announcement announcement, Job job, final int numberOfCandidates, final int index) {
        return AnnouncementModel.builder()
                .id(announcement.getId())
                .jobName(job.getName())
                .jobType(job.getType())
                .jobLevel(job.getLevel())
                .skills(announcement.getSkills())
                .description(announcement.getDescription())
                .benefit(announcement.getBenefit())
                .requirement(announcement.getRequirement())
                .responsability(announcement.getResponsability())
                .index(index)
                .numberOfCandidates(numberOfCandidates)
                .build();
    }

    public static Announcement toEntry(final AnnouncementModel announcementModel) {
        return Announcement.builder()
                .skills(announcementModel.getSkills())
                .description(announcementModel.getDescription())
                .benefit(announcementModel.getBenefit())
                .requirement(announcementModel.getRequirement())
                .responsability(announcementModel.getResponsability())
                .build();
    }
}
