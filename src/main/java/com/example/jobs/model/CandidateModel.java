package com.example.jobs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateModel {
    String id;
    String firstName;
    String lastName;
    String email;
    String phone;
    String appliedDate;
    String accepted;
    String decision;
    Boolean interview;
    Boolean isAccepted;
}
