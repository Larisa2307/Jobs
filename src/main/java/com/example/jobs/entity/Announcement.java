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
    @ManyToOne
    @JoinColumn(name = "job_id")
    Job job;
    @CreationTimestamp
    LocalDate datePosted;
    String description;
    String responsability;
    String requirement;
    String benefit;
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;


}
