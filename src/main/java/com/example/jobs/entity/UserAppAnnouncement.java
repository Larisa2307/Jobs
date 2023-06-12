package com.example.jobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserAppAnnouncement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    Announcement announcement;
    @ManyToOne
    @JoinColumn(name = "user_app_id")
    UserApp userApp;
    String accepted;

    @CreationTimestamp
    LocalDate appliedDate;
}
