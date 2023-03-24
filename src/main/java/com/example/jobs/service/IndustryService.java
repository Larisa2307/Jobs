package com.example.jobs.service;

import com.example.jobs.entity.Industry;
import com.example.jobs.repository.IndustryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IndustryService {
    final IndustryRepository industryRepository;

    public List<Industry> findAll() {
        return industryRepository.findAll();
    }

    public Industry findById(String id) {
        return industryRepository.findById(id).get();
    }
}
