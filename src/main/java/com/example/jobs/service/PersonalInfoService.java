package com.example.jobs.service;

import com.example.jobs.entity.UserApp;
import com.example.jobs.entity.PersonalInfo;
import com.example.jobs.repository.PersonalInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PersonalInfoService {

    final PersonalInfoRepository personalInfoRepository;

    public void save(PersonalInfo personalInfo) {
        personalInfoRepository.save(personalInfo);
    }

    public Optional<PersonalInfo> getUserInfoByUserApp(UserApp userApp) {
        return personalInfoRepository.findByUserApp(userApp);
    }
}
