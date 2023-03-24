package com.example.jobs.repository;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.AnnouncementSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementSkillRepository extends JpaRepository<AnnouncementSkill, String> {

    List<AnnouncementSkill> getAnnouncementSkillByAnnouncement(Announcement announcementId);
}
