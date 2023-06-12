package com.example.jobs.repository;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.UserAppAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAppAnnouncementRepository extends JpaRepository<UserAppAnnouncement, String> {

    @Query("select ua.userApp from UserAppAnnouncement ua join Announcement a on ua.announcement = a where a = ?1")
    List<UserApp> findUserAppByAnnouncement(Announcement announcement);

    @Query("select ua from UserAppAnnouncement ua where ua.userApp = ?1 and ua.announcement = ?2")
    Optional<UserAppAnnouncement> findByUserAndAnnouncement(UserApp userApp, Announcement announcement);

    @Query("select ua from UserAppAnnouncement ua where ua.userApp = ?1")
    List<UserAppAnnouncement> findAnnouncementsByUserApp(UserApp userApp);
}
