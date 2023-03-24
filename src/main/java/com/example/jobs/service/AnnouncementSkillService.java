package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.AnnouncementSkill;
import com.example.jobs.repository.AnnouncementSkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AnnouncementSkillService {
    final AnnouncementSkillRepository announcementSkillRepository;

    public void saveAnnouncementSkills(AnnouncementSkill announcementSkill) {
        announcementSkillRepository.save(announcementSkill);
    }

    public void deleteAnnouncementSkills(AnnouncementSkill announcementSkill) {
        announcementSkillRepository.delete(announcementSkill);
    }

    public List<AnnouncementSkill> getSkillsByAnnouncement(Announcement announcement) {
        return announcementSkillRepository.getAnnouncementSkillByAnnouncement(announcement);
    }

}
