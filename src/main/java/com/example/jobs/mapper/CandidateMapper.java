package com.example.jobs.mapper;

import com.example.jobs.entity.UserAppAnnouncement;
import com.example.jobs.model.CandidateModel;
import com.example.jobs.util.DateFormat;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CandidateMapper {
    public static CandidateModel toModel(final UserAppAnnouncement userAppAnnouncement) {
        return CandidateModel.builder()
                .id(userAppAnnouncement.getUserApp().getId())
                .firstName(userAppAnnouncement.getUserApp().getFirstName())
                .lastName(userAppAnnouncement.getUserApp().getLastName())
                .appliedDate(DateFormat.dateformat(userAppAnnouncement.getAppliedDate()))
                .phone(userAppAnnouncement.getUserApp().getPhone())
                .email(userAppAnnouncement.getUserApp().getEmail())
                .accepted(userAppAnnouncement.getAccepted())
                .decision(userAppAnnouncement.getDecision())
                .interview("Interview".equals(userAppAnnouncement.getDecision()) ||
                        "Accepted".equals(userAppAnnouncement.getDecision()) ||
                        "Rejected".equals(userAppAnnouncement.getDecision()))
                .isAccepted("Accepted".equals(userAppAnnouncement.getDecision()) ||
                        "Rejected".equals(userAppAnnouncement.getDecision()))
                .build();
    }
}
