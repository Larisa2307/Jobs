package com.example.jobs.service;

import com.example.jobs.entity.Education;
import com.example.jobs.entity.UserApp;
import com.example.jobs.mapper.EducationMapper;
import com.example.jobs.model.EducationModel;
import com.example.jobs.repository.EducationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EducationService {
    final EducationRepository educationRepository;

    public void save(Education education) {
        educationRepository.save(education);
    }

    public void delete(Education education) {
        educationRepository.delete(education);
    }

    public Education getById(String id) {
        return educationRepository.findById(id).get();
    }

    public List<Education> getAllByUserApp(UserApp userApp) {
        return educationRepository.findAllByUserApp(userApp);
    }

    public List<EducationModel> getEducationModelList(UserApp userApp) {
        var educations = educationRepository.findAllByUserApp(userApp);

        return educations.stream()
                .map(EducationMapper::toModel)
                .toList();
    }
}
