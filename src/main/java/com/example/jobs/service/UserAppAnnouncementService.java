package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.UserAppAnnouncement;
import com.example.jobs.mapper.CandidateMapper;
import com.example.jobs.model.CandidateModel;
import com.example.jobs.repository.UserAppAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserAppAnnouncementService {
    final UserAppAnnouncementRepository userAppAnnouncementRepository;

    public void save(UserAppAnnouncement userAppAnnouncement) {
        userAppAnnouncementRepository.save(userAppAnnouncement);
    }

    public List<UserApp> getUserAppByAnnouncement(Announcement announcement) {
        return userAppAnnouncementRepository.findUserAppByAnnouncement(announcement);
    }

    public Optional<UserAppAnnouncement> getUserAppAnnouncementByUserAndAnnouncement(UserApp userApp, Announcement announcement) {
        return userAppAnnouncementRepository.findByUserAndAnnouncement(userApp, announcement);
    }

    public List<UserAppAnnouncement> getAnnouncementsByUserApp(UserApp userApp) {
        return userAppAnnouncementRepository.findAnnouncementsByUserApp(userApp);
    }

    private CandidateModel toCandidateModel(UserAppAnnouncement userAppAnnouncement, String available) {
        if (userAppAnnouncement.getAccepted() == null) {
            userAppAnnouncement.setAccepted("Pending response");
        }
        return CandidateMapper.toModel(userAppAnnouncement, available);
    }

    public List<CandidateModel> getCandidateModelList(UserApp userApp) {
        var announcementsByUserApp = userAppAnnouncementRepository.findAnnouncementsByUserApp(userApp);

        return announcementsByUserApp.stream()
                .map(userAppAnnouncement -> {
                    var available = userAppAnnouncement.getAnnouncement().getDateEnded().isAfter(LocalDate.now()) ? "Yes" : "No";
                    return toCandidateModel(userAppAnnouncement, available);
                })
                .toList();
    }
}
