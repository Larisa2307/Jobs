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
public class AnnouncementSkill {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    Announcement announcement;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    Skill skill;
}
