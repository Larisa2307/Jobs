package com.example.jobs.repository;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {


    List<Announcement> findAnnouncementByEmployer(Employer employer);

    @Query("select a from Announcement a where lower(a.jobName) = lower(?1) ")
    Optional<Announcement> existsByJobName(String jobName);

    @Query("select a from Announcement a where lower(a.jobName) = lower(?1) and a.id <> ?2")
    Optional<Announcement> existsByName(String name, String id);


}
