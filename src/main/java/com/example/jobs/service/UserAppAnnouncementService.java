package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.UserAppAnnouncement;
import com.example.jobs.repository.UserAppAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
