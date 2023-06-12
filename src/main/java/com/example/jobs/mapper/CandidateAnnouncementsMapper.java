package com.example.jobs.mapper;

import com.example.jobs.entity.UserAppAnnouncement;
import com.example.jobs.model.CandidateAnnouncementsModel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CandidateAnnouncementsMapper {

    public static CandidateAnnouncementsModel toModel(final UserAppAnnouncement userAppAnnouncement, final String available) {
        return CandidateAnnouncementsModel.builder()
                .id(userAppAnnouncement.getAnnouncement().getId())
                .jobName(userAppAnnouncement.getAnnouncement().getJob().getName())
                .jobType(userAppAnnouncement.getAnnouncement().getJob().getType())
                .jobLevel(userAppAnnouncement.getAnnouncement().getJob().getLevel())
                .datePosted(userAppAnnouncement.getAnnouncement().getDatePosted())
                .dateEnded(userAppAnnouncement.getAnnouncement().getDateEnded())
                .accepted(userAppAnnouncement.getAccepted())
                .available(available)
                .companyName(userAppAnnouncement.getAnnouncement().getJob().getCompany().getCompanyName())
                .build();
    }
}
