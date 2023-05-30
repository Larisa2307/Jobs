package com.example.jobs.repository;

import com.example.jobs.entity.PersonalInfo;
import com.example.jobs.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, String> {

    Optional<PersonalInfo> findByUserApp(UserApp userApp);
}
