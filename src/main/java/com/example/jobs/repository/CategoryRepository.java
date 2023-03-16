package com.example.jobs.repository;

import com.example.jobs.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findAllByOrderByName();

    @Query("select c from Category c where lower(c.name) = lower(?1)")
    Optional<Category> existsByName(String name);

    @Query("select c from Category c where lower(c.name) = lower(?1) and c.id <> ?2")
    Optional<Category> existsByNameExcept(String name, String id);
}
