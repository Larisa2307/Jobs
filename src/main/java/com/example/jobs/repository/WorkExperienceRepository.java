package com.example.jobs.repository;

import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, String> {

    List<WorkExperience> findAllByUserApp(UserApp userApp);
}
