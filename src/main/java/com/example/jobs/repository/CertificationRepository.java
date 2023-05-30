package com.example.jobs.repository;

import com.example.jobs.entity.Certification;
import com.example.jobs.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, String> {

    List<Certification> findAllByUserApp(UserApp userApp);
}
