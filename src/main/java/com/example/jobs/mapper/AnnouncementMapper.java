package com.example.jobs.mapper;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import com.example.jobs.model.AnnouncementModel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AnnouncementMapper {

    public static AnnouncementModel toModel(final Announcement announcement, final Job job, final Company company,
                                            final String available, final int numberOfCandidates, final int index) {
        return AnnouncementModel.builder()
                .id(announcement.getId())
                .jobName(job.getName())
                .jobType(job.getType())
                .jobLevel(job.getLevel())
                .datePosted(announcement.getDatePosted())
                .dateEnded(announcement.getDateEnded())
                .skills(announcement.getSkills())
                .description(announcement.getDescription())
                .benefit(announcement.getBenefit())
                .hasBenefits("".equals(announcement.getBenefit()) || announcement.getBenefit() == null)
                .requirement(announcement.getRequirement())
                .responsibility(announcement.getResponsibility())
                .index(index)
                .numberOfCandidates(numberOfCandidates)
                .available(available)
                .companyName(company.getCompanyName())
                .companyEmail(company.getEmail())
                .companyDescription(company.getDescription())
                .companyCity(company.getCity())
                .companyCountry(company.getCountry())
                .companyPhone(company.getPhoneNumber())
                .build();
    }

    public static Announcement toEntry(final AnnouncementModel announcementModel) {
        return Announcement.builder()
                .dateEnded(announcementModel.getDateEnded())
                .skills(announcementModel.getSkills())
                .description(announcementModel.getDescription())
                .benefit(announcementModel.getBenefit())
                .requirement(announcementModel.getRequirement())
                .responsibility(announcementModel.getResponsibility())
                .build();
    }
}
