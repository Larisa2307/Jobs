package com.example.jobs.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateModel {
    String id;
    Integer index;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String accepted;
}
