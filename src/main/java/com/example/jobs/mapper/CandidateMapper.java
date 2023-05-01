package com.example.jobs.mapper;

import com.example.jobs.entity.Job;
import com.example.jobs.entity.UserApp;
import com.example.jobs.model.CandidateModel;
import com.example.jobs.model.JobModel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CandidateMapper {
    public static CandidateModel toModel(final UserApp userApp, final int index, final String accepted) {
        return CandidateModel.builder()
                .id(userApp.getId())
                .firstName(userApp.getFirstName())
                .lastName(userApp.getLastName())
                .email(userApp.getEmail())
                .phoneNumber(userApp.getPhone())
                .accepted(accepted)
                .index(index)
                .build();
    }

    public static UserApp toEntry(final CandidateModel candidateModel) {
        return UserApp.builder()
                .firstName(candidateModel.getFirstName())
                .lastName(candidateModel.getLastName())
                .email(candidateModel.getEmail())
                .email(candidateModel.getEmail())
                .build();
    }
}
