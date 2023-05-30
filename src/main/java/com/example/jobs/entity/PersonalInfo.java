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
public class PersonalInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String location;
    String language;
    String mainAreas;
    String skills;
    @ManyToOne
    @JoinColumn(name = "user_app_id")
    UserApp userApp;

}
