package com.example.jobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String name;
    String type;
    String level;
    Long salary;
    Integer numberOfVacantSpots;
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;
}
