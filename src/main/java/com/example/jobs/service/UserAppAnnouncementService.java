package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.UserApp;
import com.example.jobs.repository.UserAppAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserAppAnnouncementService {
    final UserAppAnnouncementRepository userAppAnnouncementRepository;

    public List<UserApp> getUserAppByAnnouncement(Announcement announcement) {
        return userAppAnnouncementRepository.findUserAppByAnnouncement(announcement);
    }
}
