package com.example.jobs.service;

import com.example.jobs.entity.Skill;
import com.example.jobs.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SkillService {
    final SkillRepository skillRepository;

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Skill findSkillById(String id) {
        return skillRepository.findById(id).get();
    }
}
