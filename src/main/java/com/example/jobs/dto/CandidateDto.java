package com.example.jobs.dto;

import com.example.jobs.entity.UserApp;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {
    UserApp candidate;
    String accepted;
}
