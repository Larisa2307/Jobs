package com.example.jobs.repository;

import com.example.jobs.dto.CandidateDto;
import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.UserAppAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAppAnnouncementRepository extends JpaRepository<UserAppAnnouncement, String> {

    @Query("select ua.userApp from UserAppAnnouncement ua join Announcement a on ua.announcement = a where a = ?1")
    List<UserApp> findUserAppByAnnouncement(Announcement announcement);

    @Query("select ua.userApp, ua.accepted from UserAppAnnouncement ua join Announcement a on ua.announcement = a where a = ?1")
    List<CandidateDto> findCandidatesByAnnouncement(Announcement announcement);
}
