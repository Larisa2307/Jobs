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
public class UserInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String mainAreas;
    String skills;
    @ManyToOne
    @JoinColumn(name = "user_app_id")
    UserApp userApp;
    @ManyToOne
    @JoinColumn(name = "work_experience_id")
    WorkExperience workExperience;
    @ManyToOne
    @JoinColumn(name = "certification_id")
    Certification certification;
    @ManyToOne
    @JoinColumn(name = "enducation_id")
    Education education;

}
