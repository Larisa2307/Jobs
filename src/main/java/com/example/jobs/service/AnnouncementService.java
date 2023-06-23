package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import com.example.jobs.mapper.AnnouncementMapper;
import com.example.jobs.model.AnnouncementModel;
import com.example.jobs.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class AnnouncementService {

    final AnnouncementRepository announcementRepository;
    final UserAppAnnouncementService userAppAnnouncementService;
    final JobService jobService;

    public Announcement getAnnouncementById(String announcementId) {
        return announcementRepository.findById(announcementId).get();
    }

    public void deleteAnnouncement(String announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    public Boolean isDuplicate(Job job) {
        return announcementRepository.existsByJob(job).isPresent();
    }

    public void saveNewAnnouncement(AnnouncementModel announcementModel, Job job) {
        Announcement announcement = toAnnouncement(announcementModel, job);
        announcement.setId(announcementModel.getId());
        announcementRepository.save(announcement);
    }

    private Announcement toAnnouncement(AnnouncementModel announcementModel, Job job) {
        Announcement announcement = AnnouncementMapper.toEntry(announcementModel);
        announcement.setJob(job);
        return announcement;
    }

    private AnnouncementModel toAnnouncementModel(Announcement announcement, Job job, String available, int index) {
        var numberOfCandidates = userAppAnnouncementService.getUserAppByAnnouncement(announcement).size();
        return AnnouncementMapper.toModel(announcement, job, job.getCompany(), available, numberOfCandidates, index);
    }

    public Announcement getAnnouncementByJob(Job job) {
        return announcementRepository.findByJob(job);
    }

    public List<AnnouncementModel> getAnnouncementModelList(Company company) {
        var announcements = announcementRepository.findByCompany(company);

        var index = new AtomicInteger(1);
        return announcements.stream()
                .map(announcement -> {
                    var available = announcement.getDateEnded().isAfter(LocalDate.now()) ? "Yes" : "No";
                    return toAnnouncementModel(announcement, announcement.getJob(), available, index.getAndIncrement());
                })
                .toList();
    }

    public List<AnnouncementModel> getAvailableAnnouncements() {
        var announcements = announcementRepository.findAll();

        var index = new AtomicInteger(1);
        return announcements.stream()
                .map(announcement -> {
                    if (announcement.getDateEnded().isAfter(LocalDate.now())) {
                        return toAnnouncementModel(announcement, announcement.getJob(), "Yes", index.getAndIncrement());
                    }
                    return null;
                })
                .toList();
    }

    public AnnouncementModel getAnnouncementModelById(String id) {
        var announcement = announcementRepository.findById(id).get();
        var available = announcement.getDateEnded().isAfter(LocalDate.now()) ? "Yes" : "No";
        return toAnnouncementModel(announcement, announcement.getJob(), available, 1);
    }
}
