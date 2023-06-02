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
public class Education {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String university;
    String degree;
    String speciality;
    LocalDate startDate;
    LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "user_app_id")
    UserApp userApp;
}
