package com.example.jobs.repository;

import com.example.jobs.entity.Document;
import com.example.jobs.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    Optional<Document> findByUserApp(UserApp userApp);

    Document findByName(String name);
}
