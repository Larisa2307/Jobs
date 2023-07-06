package com.example.jobs.service;

import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.WorkExperience;
import com.example.jobs.mapper.WorkExperienceMapper;
import com.example.jobs.model.WorkExperienceModel;
import com.example.jobs.repository.WorkExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class WorkExperienceService {
    final WorkExperienceRepository workExperienceRepository;

    public void save(WorkExperience workExperience) {
        workExperienceRepository.save(workExperience);
    }

    public void delete(WorkExperience workExperience) {
        workExperienceRepository.delete(workExperience);
    }

    public WorkExperience getById(String id) {
        return workExperienceRepository.findById(id).get();
    }

    public List<WorkExperienceModel> getWorkExperienceModelList(UserApp userApp) {
        var workExperiences = workExperienceRepository.findAllByUserApp(userApp);

        return workExperiences.stream()
                .map(WorkExperienceMapper::toModel)
                .toList();
    }
}
