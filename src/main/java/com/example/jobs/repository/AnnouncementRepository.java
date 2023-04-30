package com.example.jobs.repository;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {

    @Query("select a from Announcement a where a.job = ?1")
    Optional<Announcement> existsByJob(Job job);

    @Query("select a from Announcement a join Job j on a.job = j where j.company = ?1")
    List<Announcement> findByCompany(Company company);


}
