package com.example.jobs.repository;

import com.example.jobs.entity.Education;
import com.example.jobs.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, String> {

    List<Education> findAllByUserApp(UserApp userApp);
}
