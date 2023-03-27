package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.AnnouncementSkill;
import com.example.jobs.entity.Employer;
import com.example.jobs.mapper.AnnouncementMapper;
import com.example.jobs.model.AnnouncementModel;
import com.example.jobs.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class AnnouncementService {

    final SkillService skillService;
    final IndustryService industryService;
    final AnnouncementRepository announcementRepository;
    final AnnouncementSkillService announcementSkillService;
    final UserAppAnnouncementService userAppAnnouncementService;

    public Announcement getAnnouncementById(String announcementId) {
        return announcementRepository.findById(announcementId).get();
    }

    public void deleteAnnouncement(String announcementId){
        announcementRepository.deleteById(announcementId);
    }

    public List<Announcement> getCompanyAnnouncements(Employer employer) {
        return announcementRepository.findAnnouncementByEmployer(employer);
    }

    public Boolean isDuplicate(String jobName) {
        return announcementRepository.existsByJobName(jobName).isPresent();
    }

    public Boolean isDuplicateName(String name, String id) {
        return announcementRepository.existsByName(name, id).isPresent();
    }

    private Announcement toAnnouncement(AnnouncementModel announcementModel, Employer employer) {
        Announcement announcement = AnnouncementMapper.toEntry(announcementModel);
        announcement.setIndustries(industryService.findById(announcementModel.getIndustries()));
        announcement.setEmployer(employer);

        if (announcementModel.getAnnouncementSkills() != null) {
            announcementModel.getAnnouncementSkills().forEach(skillId -> {
                var announcementSkill = new AnnouncementSkill();
                announcementSkill.setAnnouncement(announcement);
                announcementSkill.setSkill(skillService.findSkillById(skillId));
                announcementSkillService.saveAnnouncementSkills(announcementSkill);
            });
        }
        return announcement;
    }

    private AnnouncementModel toAnnouncementModel(Announcement announcement, int index) {
        var skills = announcementSkillService.getSkillsByAnnouncement(announcement)
                .stream().map(as -> as.getSkill().getName()).toList();
        var numberOfCandidates = userAppAnnouncementService.getUserAppByAnnouncement(announcement).size();
        return AnnouncementMapper.toModel(announcement, skills, numberOfCandidates, index);
    }

    public void saveNewAnnouncement(AnnouncementModel announcementModel, Employer employer) {
        Announcement announcement = toAnnouncement(announcementModel, employer);
        announcement.setId(announcementModel.getId());
        announcementRepository.save(announcement);

    }

    public List<AnnouncementModel> getAnnouncementModelList(Employer employer) {
        var announcements = announcementRepository.findAnnouncementByEmployer(employer);

        var index = new AtomicInteger(1);
        return announcements.stream()
                .map(announcement -> toAnnouncementModel(announcement, index.getAndIncrement()))
                .toList();
    }
}
