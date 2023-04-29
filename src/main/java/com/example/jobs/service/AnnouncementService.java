package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Company;
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

    final AnnouncementRepository announcementRepository;
    final UserAppAnnouncementService userAppAnnouncementService;

    public Announcement getAnnouncementById(String announcementId) {
        return announcementRepository.findById(announcementId).get();
    }

    public void deleteAnnouncement(String announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    public Boolean isDuplicate(String jobName) {
        return announcementRepository.existsByJobName(jobName).isPresent();
    }

    public Boolean isDuplicateName(String name, String id) {
        return announcementRepository.existsByName(name, id).isPresent();
    }

    private Announcement toAnnouncement(AnnouncementModel announcementModel, Company company) {
        Announcement announcement = AnnouncementMapper.toEntry(announcementModel);
        announcement.setCompany(company);

        return announcement;
    }

    private AnnouncementModel toAnnouncementModel(Announcement announcement, int index) {
        var numberOfCandidates = userAppAnnouncementService.getUserAppByAnnouncement(announcement).size();
        return AnnouncementMapper.toModel(announcement, numberOfCandidates, index);
    }

    public void saveNewAnnouncement(AnnouncementModel announcementModel, Company company) {
        Announcement announcement = toAnnouncement(announcementModel, company);
        announcement.setId(announcementModel.getId());
        announcementRepository.save(announcement);

    }

    public List<AnnouncementModel> getAnnouncementModelList(Company company) {
        var announcements = announcementRepository.findAnnouncementByCompany(company);

        var index = new AtomicInteger(1);
        return announcements.stream()
                .map(announcement -> toAnnouncementModel(announcement, index.getAndIncrement()))
                .toList();
    }
}
