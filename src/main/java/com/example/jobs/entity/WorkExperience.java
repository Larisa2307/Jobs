package com.example.jobs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperience {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String companyName;
    String role;
    LocalDate startDate;
    LocalDate endDate;
    String projectsDescription;
    @ManyToOne
    @JoinColumn(name = "user_app_id")
    UserApp userApp;
}
