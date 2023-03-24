package com.example.jobs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String jobName;
    String jobType;
    String jobLevel;
    @CreationTimestamp
    LocalDate datePosted;
    String description;
    String responsability;
    String requirement;
    String benefit;
    @ManyToOne
    @JoinColumn(name = "industry_id")
    Industry industries;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    Employer employer;


}
