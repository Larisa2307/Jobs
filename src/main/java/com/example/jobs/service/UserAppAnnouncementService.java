package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.UserAppAnnouncement;
import com.example.jobs.mapper.CandidateAnnouncementsMapper;
import com.example.jobs.mapper.CandidateMapper;
import com.example.jobs.model.CandidateAnnouncementsModel;
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

    private CandidateAnnouncementsModel toCandidateAnnouncementsModel(UserAppAnnouncement userAppAnnouncement, String available) {
        if (userAppAnnouncement.getAccepted() == null) {
            userAppAnnouncement.setAccepted("Pending response");
        }

        return CandidateAnnouncementsMapper.toModel(userAppAnnouncement, available);
    }

    private CandidateModel toCandidateModel(UserAppAnnouncement userAppAnnouncement) {
        if (userAppAnnouncement.getAccepted() == null) {
            userAppAnnouncement.setAccepted("Pending response");
        }

        if (userAppAnnouncement.getDecision() == null) {
            userAppAnnouncement.setDecision("Pending response");
        }

        return CandidateMapper.toModel(userAppAnnouncement);
    }

    public List<CandidateAnnouncementsModel> getAnnouncementModelList(UserApp userApp) {
        var announcementsByUserApp = userAppAnnouncementRepository.findAnnouncementsByUserApp(userApp);

        return announcementsByUserApp.stream()
                .map(userAppAnnouncement -> {
                    var available = userAppAnnouncement.getAnnouncement().getDateEnded().isAfter(LocalDate.now()) ? "Yes" : "No";
                    return toCandidateAnnouncementsModel(userAppAnnouncement, available);
                })
                .toList();
    }

    public List<CandidateModel> getCandidateModelList(Announcement announcement) {
        var announcementsByUserApp = userAppAnnouncementRepository.findUsersAppByAnnouncement(announcement);

        return announcementsByUserApp.stream()
                .map(this::toCandidateModel)
                .toList();
    }

    public CandidateModel getCandidateModelByAnnouncement(UserApp userApp, Announcement announcement) {
        var announcementByUserApp = userAppAnnouncementRepository
                .findByUserAndAnnouncement(userApp, announcement);

        return toCandidateModel(announcementByUserApp.get());
    }

}
